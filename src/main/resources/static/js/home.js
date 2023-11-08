// 지도 객체
let map = null;
// 지도 마커
const markers = [];
// 우편번호 찾기 화면을 넣을 element
const elementLayer = document.getElementById("layer");
// 마커를 클릭하면 표시되는 정보 툴팁
const infoWindow = new naver.maps.InfoWindow({
  maxWidth: 300,
});

$(document).ready(() => {
  initMap();
});

function initMap() {
  const position = localStorage.getItem("lastPosition");
  map = new naver.maps.Map("map", {
    center: position
      ? JSON.parse(position)
      : createNaverMapPosition(37.5665, 126.978),
    zoom: 15,
  });

  // 무조건 주소를 검색하는 방식으로 변경되어 주석 처리
  // initMapClickEventHandler(map);
  initSavedMarkers();
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
        const position = createNaverMapPosition(
          building.latitude,
          building.longitude
        );
        addMarker(position, {
          id: building.id,
          name: building.name,
          address: building.address,
        });
      }
    },
    error: function (data) {
      console.error("저장된 마커 초기화 실패");
    },
  });
}

function createNaverMapPosition(latitude, longitude) {
  return new naver.maps.LatLng(latitude, longitude);
}

// 마커를 클릭했을 때 InfoWindow를 열고 "상세보기" 또는 "등록하기" 또는 "삭제하기" 버튼 표시
function addClickEventListenerToMarker(marker) {
  naver.maps.Event.addListener(marker, "click", function () {
    const id = marker.get("id");
    const name = marker.get("name");
    const address = marker.get("address");

    let content = "<div>";

    if (name) {
      content += "<h4>" + name + "</h4>";
    }

    if (address) {
      content += "<p>" + address + "</p>";
    }

    if (id) {
      content += '<button id="detailButton" value="' + id + '">보기</button>';
      content += '<button id="deleteMarker" value="' + id + '">삭제</button>';
    } else {
      content += '<button id="registerButton">등록</button>';
      content += '<button id="deleteMarker">삭제</button>';
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

    const position = marker.getPosition();
    localStorage.setItem("lastPosition", JSON.stringify(position));
  });
}

function addMarker(position, buildingInfo) {
  const marker = new naver.maps.Marker({
    position,
    map: map,
  });

  if (buildingInfo) {
    if (buildingInfo.id) {
      marker.set("id", buildingInfo.id);
    }

    if (buildingInfo.name) {
      marker.set("name", buildingInfo.name);
    }

    if (buildingInfo.address) {
      marker.set("address", buildingInfo.address);
    }
  }

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
      coords: createNaverMapPosition(_lat, _lng),
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

function closeDaumPostcode() {
  elementLayer.style.display = "none";
}

function execDaumPostcode() {
  new daum.Postcode({
    oncomplete: function (data) {
      let addr = ""; // 주소 변수

      //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
      if (data.userSelectedType === "R") {
        // 사용자가 도로명 주소를 선택했을 경우
        addr = data.roadAddress;
      } else {
        // 사용자가 지번 주소를 선택했을 경우(J)
        addr = data.jibunAddress;
      }

      if (addr) {
        getPositionFromAddress(addr).then((position) => {
          if (position) {
            addMarker(position, {
              id: null,
              name: data.buildingName,
              address: data.address,
            });
            if (map) {
              map.setCenter(position);
            }
          }
        });
      }

      elementLayer.style.display = "none";
    },
    width: "100%",
    height: "100%",
    maxSuggestItems: 5,
  }).embed(elementLayer);

  // iframe을 넣은 element를 보이게 한다.
  elementLayer.style.display = "block";

  // iframe을 넣은 element의 위치를 화면의 가운데로 이동시킨다.
  initLayerPosition();
}

function getPositionFromAddress(query) {
  return new Promise((resolve) => {
    naver.maps.Service.geocode(
      {
        query,
      },
      function (status, response) {
        if (status !== naver.maps.Service.Status.OK) {
          return alert("Something wrong!");
        }

        var result = response.v2,
          items = result.addresses;

        const item = items[0];
        resolve(createNaverMapPosition(item.y, item.x));
      }
    );
  });
}

function initLayerPosition() {
  const width = 300;
  const height = 400;
  const borderWidth = 5;

  // 위에서 선언한 값들을 실제 element에 넣는다.
  elementLayer.style.width = width + "px";
  elementLayer.style.height = height + "px";
  elementLayer.style.border = borderWidth + "px solid";
  // 실행되는 순간의 화면 너비와 높이 값을 가져와서 중앙에 뜰 수 있도록 위치를 계산한다.
  elementLayer.style.left =
    ((window.innerWidth || document.documentElement.clientWidth) - width) / 2 -
    borderWidth +
    "px";
  elementLayer.style.top =
    ((window.innerHeight || document.documentElement.clientHeight) - height) /
      2 -
    borderWidth +
    "px";
}

// 지도를 클릭하면 좌표를 받아 마커 표시
// function initMapClickEventHandler(map) {
//   naver.maps.Event.addListener(map, "click", function (e) {
//     addMarker(e.coord);
//   });
// }
