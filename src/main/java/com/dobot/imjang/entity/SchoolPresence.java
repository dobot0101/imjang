package main.java.com.dobot.imjang.entity;

@Entity
public class SchoolPresence {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "school_type")
  @Enumerated(EnumType.String)
  private SchoolType schoolType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "building_id")
  private Building building;

  // Getters and setters...
}


public enum SchoolType {
  ELEMENTARY, // 초등학교
  MIDDLE, // 중학교
  HIGH // 고등학교
}