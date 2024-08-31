package com.dobot.imjang.domain.building;

import com.dobot.imjang.domain.building.enums.*;
import com.dobot.imjang.domain.common.entities.BaseTime;
import com.dobot.imjang.domain.member.Member;
import com.dobot.imjang.domain.unit.Unit;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// 기본 생성자를 생성하지 못하도록 하여 객체 생성시 Builder 를 사용하도록 함
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// @AllArgsConstructor 를 사용하면 멤버 필드의 순서대로 생성자의 파라미터가 결정되기 때문에 같은 타입의 필드 순서를 변경하면 예기치 않은 에러가 발생할 수 있음
// @Builder를 사용하려면 @AllArgsConstructor가 필요한데 @AllArgsConstructor는 내부적으로 생성자를 생성하는데, 빌더 사용을 강제하기 위해 access = AccessLevel.PRIVATE 옵션으로 접근 제한자를 private으로 설정함
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Getter
@Table(indexes = {
        @Index(columnList = "latitude, longitude", unique = true)
})
public class Building extends BaseTime {
    @Setter
    @Id
    private UUID id;

    // 좌표(위도)
    @Setter
    @Column(nullable = false)
    private double latitude;

    // 좌표(경도)
    @Setter
    @Column(nullable = false)
    private double longitude;

    // 건물 이름 (예: 아파트 단지 이름)
    @Setter
    @Column(nullable = false, length = 100)
    private String name;

    // 건물 주소
    @Setter
    @Column(nullable = false, length = 200)
    private String address;

    // 엘리베이터(있음, 없음)
    @Setter
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private ElevatorStatus elevatorStatus;

    // 현관구조(계단식, 복도식)
    @Setter
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private EntranceStructure entranceStructure;

    // 주차공간(많음, 적음, 보통)
    @Setter
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private ParkingSpace parkingSpace;

    // 학군
    @Builder.Default
    @OneToMany(mappedBy = "building", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SchoolDistrict> schoolDistricts = new ArrayList<>();

    // 주변시설
    @Builder.Default
    @OneToMany(mappedBy = "building", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Facility> facilities = new ArrayList<>();

    // 교통수단
    @Builder.Default
    @OneToMany(mappedBy = "building", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Transportation> transportations = new ArrayList<>();

    @JsonManagedReference
    @Builder.Default
    @OneToMany(mappedBy = "building", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Unit> units = new ArrayList<>();

    @Setter
    @ManyToOne()
    @JoinColumn(name = "member_id")
    private Member member;

    public static Building create(CreateBuildingDto dto, Member member) {
        Building building = Building.builder()
                .id(UUID.randomUUID())
                .longitude(dto.getLongitude())
                .latitude(dto.getLatitude())
                .member(member)
                .address(dto.getAddress())
                .name(dto.getName())
                .entranceStructure(dto.getEntranceStructure())
                .elevatorStatus(dto.getElevatorStatus())
                .parkingSpace(dto.getParkingSpace())
                .build();
        building.setAdditionalInformation(dto);
        return building;
    }

    public void update(UpdateBuildingDto dto) {
        setAddress(dto.getAddress());
        setName(dto.getName());
        setEntranceStructure(dto.getEntranceStructure());
        setElevatorStatus(dto.getElevatorStatus());
        setParkingSpace(dto.getParkingSpace());
        this.setAdditionalInformation(dto);
    }

    public void setAdditionalInformation(IBuildingDto dto) {
        setSchoolDistricts(dto.getSchoolTypes());
        setFacilities(dto.getFacilityTypes());
        setTransportations(dto.getTransportationTypes());
    }

    private void setSchoolDistricts(List<SchoolType> types) {
        if (types == null) {
            return;
        }
        List<SchoolDistrict> newSchoolDistricts = new ArrayList<>();
        for (var type : types) {
            schoolDistricts.stream().filter(schoolDistrict -> schoolDistrict.getSchoolType().equals(type)).findFirst()
                    .ifPresentOrElse(newSchoolDistricts::add,
                            () -> schoolDistricts.add(SchoolDistrict.builder().id(UUID.randomUUID()).schoolType(type).building(this).build()));
        }
        schoolDistricts.clear();
        schoolDistricts.addAll(newSchoolDistricts);
    }

    private void setFacilities(List<FacilityType> types) {
        if (types == null) {
            return;
        }
        List<Facility> newFacilities = new ArrayList<>();
        for (var type : types) {
            facilities.stream().filter(facility -> facility.getFacilityType().equals(type)).findFirst()
                    .ifPresentOrElse(newFacilities::add,
                            () -> facilities.add(Facility.builder().id(UUID.randomUUID()).facilityType(type).building(this).build()));
        }
        facilities.clear();
        facilities.addAll(newFacilities);
    }

    private void setTransportations(List<TransportationType> types) {
        if (types == null) {
            return;
        }
        List<Transportation> newTransportations = new ArrayList<>();
        for (var type : types) {
            transportations.stream().filter(transportation -> transportation.getTransportationType().equals(type)).findFirst()
                    .ifPresentOrElse(newTransportations::add,
                            () -> transportations.add(Transportation.builder().id(UUID.randomUUID()).transportationType(type).building(this).build()));
        }
        transportations.clear();
        transportations.addAll(newTransportations);
    }

}