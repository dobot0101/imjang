import javax.persistence.*;

@Entity
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    
    String buildingNumber; // 동
    String roomNumber; // 호
    Double area; // 면적
    String memo; // 메모

    @Enumerated(EnumType.String)
    TransactionType transactionType; // 매매, 전세, 월세
    Double transactionPrice;
    Double deposit; // 월세의 경우에만 사용, 월세 보증금

    @Enumerated(EnumType.STRING)
     Direction direction; // 남향, 남동향, 남서향, 동향, 서향, 북향 

    @Enumerated(Enum.STRING)
     ViewQuality viewQuality; // 좋음, 보통, 나쁨

    @Enumerated(EnumType.STRING)
     Ventilation ventilation; // 좋음, 나쁨

    @Enumerated(EnumType.STRING)
     WaterPressure waterPressure; // 좋음, 나쁨

    @Enumerated(EnumType.STRING)
     NoiseLevel noiseLevel; // 없음, 보통, 심함

    @Enumerated(EnumType.STRING)
     CondensationMoldLevel condensationMoldLevel; // 없음, 보통, 심함

    @Enumerated(EnumType.STRING)
    private LeakStatus leakStatus; // 없음, 있음

    @ManyToOne
    @JoinColumn(name = "building_id")
    Building building;

    // Getters and setters...
}

public enum TransactionType {
  SALE,     // 매매
  JEONSE,   // 전세
  MONTHLY   // 월세
}


public enum Direction {
  SOUTH,        // 남향
  SOUTHEAST,    // 남동향
  SOUTHWEST,    // 남서향
  EAST,         // 동향
  WEST,         // 서향
  NORTH         // 북향
}
public enum ViewQuality {
  GOOD,       // 좋음
  AVERAGE,    // 보통
  BAD         // 나쁨
}

public enum Ventilation {
  GOOD,       // 좋
  BAD         // 나
}

public enum WaterPressure {
  GOOD,       // 좋음
  BAD         // 나쁨
}

public enum NoiseLevel {
  NONE,       // 없음
  NORMAL,     // 보통
  SEVERE      // 심함
}
public enum CondensationMoldLevel {
  NONE,       // 없음
  NORMAL,     // 보통
  SEVERE      // 심함
}

public enum LeakStatus {
  NONE,      // 없음
  PRESENT    // 있음
}