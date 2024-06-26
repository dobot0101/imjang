package com.dobot.imjang.domain.building;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.dobot.imjang.domain.building.enums.ElevatorStatus;
import com.dobot.imjang.domain.building.enums.EntranceStructure;
import com.dobot.imjang.domain.building.enums.ParkingSpace;
import com.dobot.imjang.domain.common.entities.BaseTime;
import com.dobot.imjang.domain.member.Member;
import com.dobot.imjang.domain.unit.Unit;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 기본 생성자를 생성하지 못하도록 하여 객체 생성시 Builder 를 사용하도록 함
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Getter
// 필요한 필드만 변경 가능하도록 필드에 @Setter 를 추가하는게 좋다
// @Setter

// @AllArgsConstructor 어노테이션을 사용하면 멤버 필드의 순서대로 생성자의 파라미터가 결정되기 때문에,
// 개발 중에 타입이 같은 멤버 필드의 순서를 실수로 변경하여도 컴파일 에러가 발생하지는 않아서 이후에 치명적인 오류로 발생할 수 있음
// @AllArgsConstructor
@Table(indexes = {
        @Index(columnList = "latitude, longitude", unique = true)
})
public class Building extends BaseTime {

    // @Builder
    // public Building(UUID id, double latitude, double longitude, String name,
    // String address,
    // ElevatorStatus elevatorStatus, EntranceStructure entranceStructure,
    // ParkingSpace parkingSpace,
    // List<SchoolDistrict> schoolDistricts, List<Facility> facilities,
    // List<Transportation> transportations,
    // List<Unit> units, Member member) {
    // this.id = id;
    // this.latitude = latitude;
    // this.longitude = longitude;
    // this.name = name;
    // this.address = address;
    // this.elevatorStatus = elevatorStatus;
    // this.entranceStructure = entranceStructure;
    // this.parkingSpace = parkingSpace;
    // this.schoolDistricts = schoolDistricts;
    // this.facilities = facilities;
    // this.transportations = transportations;
    // this.units = units;
    // this.member = member;
    // }

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
    @OneToMany(mappedBy = "building", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SchoolDistrict> schoolDistricts = new ArrayList<>();

    // 주변시설
    @OneToMany(mappedBy = "building", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Facility> facilities = new ArrayList<>();

    // 교통수단
    @OneToMany(mappedBy = "building", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Transportation> transportations = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "building", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Unit> units = new ArrayList<>();

    @Setter
    @ManyToOne()
    @JoinColumn(name = "member_id")
    private Member member;

}