var map = null;
var markers = [];
var infoWindow = new naver.maps.InfoWindow();

$(document).ready(() => {
  initMap();
});

function initMap() {
  map = new naver.maps.Map("map", {
    center: new naver.maps.LatLng(37.5665, 126.978),
    zoom: 15,
  });
  initMapClickEventHandler(map);
  initSavedMarkers();

  // 현재 위치 불러오는 속도가 느려서 일단은 위와 같이 고정 위치에서 시작하도록 수정
  // if ("geolocation" in navigator) {
  //   new Promise(function (resolve, reject) {
  //     navigator.geolocation.getCurrentPosition(resolve, reject);
  //   })
  //     .then((position) => {
  //       latitude = position.coords.latitude;
  //       longitude = position.coords.longitude;
  //       map = new naver.maps.Map("map", {
  //         center: new naver.maps.LatLng(latitude, longitude),
  //         zoom: 15,
  //       });
  //       initMapClickEventHandler(map);
  //       initSavedMarkers();
  //     })
  //     .catch((error) => {
  //       switch (error.code) {
  //         case error.PERMISSION_DENIED:
  //           console.error("위치 권한이 거부되었습니다.");
  //           break;
  //         case error.POSITION_UNAVAILABLE:
  //           console.error("위치 정보를 사용할 수 없습니다.");
  //           break;
  //         case error.TIMEOUT:
  //           console.error("요청 시간이 초과되었습니다.");
  //           break;
  //         case error.UNKNOWN_ERROR:
  //           console.error("알 수 없는 오류가 발생했습니다.");
  //           break;
  //       }
  //     });
  // } else {
  //   console.log("Geolocation을 지원하지 않는 브라우저입니다.");
  //   map = new naver.maps.Map("map", {
  //     center: new naver.maps.LatLng(37.5665, 126.978),
  //     zoom: 15,
  //   });
  //   initMapClickEventHandler(map);
  //   initSavedMarkers();
  // }
}

function initMapClickEventHandler(map) {
  naver.maps.Event.addListener(map, "click", function (e) {
    addMarker(e.coord);
  });
}

function initSavedMarkers() {
  $.ajax({
    type: "GET",
    url: "/api/buildings",
    dataType: "json",
    success: function (data) {
      if (!data.buildings) {
        return;
      }

      for (let i = 0; i < data.buildings.length; i++) {
        const building = data.buildings[i];
        var marker = new naver.maps.Marker({
          position: new naver.maps.LatLng(
            building.latitude,
            building.longitude
          ),
          map: map,
        });
        marker.set("id", building.id);
        addClickEventListenerToMarker(marker);
        markers.push(marker);
      }
    },
    error: function (data) {
      console.error("저장된 마커 초기화 실패");
    },
  });
}

// 마커를 클릭했을 때 InfoWindow를 열고 "상세보기" 또는 "등록하기" 또는 "삭제하기" 버튼 표시
function addClickEventListenerToMarker(marker) {
  naver.maps.Event.addListener(marker, "click", function () {
    const id = marker.get("id");
    var content = "<div>";
    if (id) {
      content +=
        '<button id="detailButton" value="' + id + '">상세보기</button>';
      content +=
        '<button id="deleteMarker" value="' + id + '">삭제하기</button>';
    } else {
      content += '<button id="registerButton">등록하기</button>';
      content += '<button id="deleteMarker">삭제하기</button>';
    }
    content += "</div>";
    infoWindow.setContent(content);
    infoWindow.open(map, marker);

    $("#registerButton").click(function () {
      moveToRegisterPage(marker);
    });

    $("#detailButton").click(function () {
      const id = $(this).val();
      window.location.href = `/buildings/read/${id}`;
    });

    // "마커 삭제" 버튼 클릭 시 마커 삭제
    $("#deleteMarker").click(function () {
      const id = $(this).val();
      if (id) {
        if (confirm("저장된 데이터가 삭제됩니다. 삭제하시겠습니까?")) {
          removeMarkerWithDB(marker, function () {
            removeMarker(marker);
            infoWindow.close();
          });
        }
      } else {
        removeMarker(marker);
        infoWindow.close();
      }
    });
  });
}

function addMarker(coord) {
  const marker = new naver.maps.Marker({
    position: coord,
    map: map,
  });

  addClickEventListenerToMarker(marker);
  markers.push(marker);
}

function moveToRegisterPage(marker) {
  const { _lat, _lng } = marker.getPosition();
  if (!_lat || !_lng) {
    throw new Error(`좌표 값이 없습니다.`);
  }

  let url = `/buildings/register?latitude=${_lat}&longitude=${_lng}`;

  // Reverse Geocoding을 사용하여 좌표를 주소로 변환
  naver.maps.Service.reverseGeocode(
    {
      coords: new naver.maps.LatLng(_lat, _lng),
      orders: "roadaddr",
    },
    function (status, response) {
      if (status === naver.maps.Service.Status.OK) {
        const result = response.v2,
          address = result.address,
          items = result.results;

        let buildingName = "";
        if (items.length > 0) {
          const buildingData = Object.values(items[0].land).find(
            (item) => item.type === "building"
          );
          if (buildingData) {
            if (buildingData.value) {
              buildingName = buildingData.value;
            }
          }
        }
        url += `&buildingName=${encodeURIComponent(buildingName)}`;

        let addressParam = "";
        if (address && address.roadAddress) {
          if (address.roadAddress) {
            addressParam = address.roadAddress;
          }
        }
        url += `&address=${encodeURIComponent(addressParam)}`;
      }

      window.location.href = url;
    }
  );
}

// DB에 저장돼있는 마커 삭제
function removeMarkerWithDB(marker, callbackFunc) {
  if (!marker.get("id")) {
    return false;
  }

  const id = marker.get("id");
  $.ajax({
    type: "DELETE",
    url: "/api/buildings/" + id,
    success: function (data) {
      if (data.result === "success") {
        callbackFunc();
      } else {
        console.error(data);
        alert("마커 삭제 실패");
      }
    },
    error: function (data) {
      alert("마커 삭제 실패");
    },
  });
}

// 마커가 DB에 저장돼있지 않은 경우 지도에 표시되고 있는 마커만 삭제
function removeMarker(marker) {
  var index = markers.indexOf(marker);
  if (index > -1) {
    markers.splice(index, 1);
    marker.setMap(null); // 마커 지도에서 제거
  }
}

window.navermap_authFailure = function () {
  alert("네이버 지도 인증 실패");
};
