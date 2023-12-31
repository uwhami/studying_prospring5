package com.apress.prospring5.ch7.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * 7.3 하이버네이트 애너테이션으로 ORM 매핑하기.
 * 7.3.1 단순 매핑
 *
 * @Entity 애너테이션은 해당 엔터티가 매핑된 엔터티 클래스임을 나타냄.
 * @Table  애너테이션은 Singer 엔터티 클래스가 매핑돼야 할 데이터베이스 테이블 이름을 정의함.
 * 각 매핑 애트리뷰트에는 컬럼 이름을 지정한 @Column 애너테이션을 적용한다.
 *  *테이블이름이 클래스타입과 같다면 테이블 이름을 생략, 컬럼 이름이 애트리뷰트 이름과 같다면 컬럼 이름을 생략 가능하다.
 *
 *  7.4.3 연관 관계 데이터를 조회하는 쿼리.
 *  @NamedQuery : 애너테이션으로 네임드 쿼리를 적용해 수정 <- 하이버네이트가 연관된 레코드를 조회하도록 쿼리 내에 강제하는 것.
 *  left join fetch 절을 사용해 하이버네이트가 연관 관계를 즉시 조회하도록 지정함.
 */
@Entity
@Table(name = "singer")
@NamedQueries({
        @NamedQuery(name="Singer.findAllWithAlbum",
            query="select distinct s from Singer s " +
                    "left join fetch s.albums a " +
                    "left join fetch s.instruments i"),
        @NamedQuery(name="Singer.findById",
                query="select distinct s from Singer s " +
                        "left join fetch s.albums a " +
                        "left join fetch s.instruments i " +
                        "where s.id = :id")
})
public class Singer implements Serializable {

    private Long singerId;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private int version;

    /** 7.3.2 일대다 매핑. Singer 엔ㅌ터티와 Album 엔터티의 일대다 관계. */
    private Set<Album> albums = new HashSet<>();

    /** 7.3.3 다대다 매핑. */
    private Set<Instrument> instruments = new HashSet<>();

    @Id //객체의 기본키 임을 뜻함.
    @GeneratedValue(strategy = IDENTITY) //id값이 등록 도중 벡엔드에서 생성됨을 뜻함.
    @Column(name = "SINGER_ID")
    public Long getId() {
        return singerId;
    }

    public void setId(Long singerId) {
        this.singerId = singerId;
    }

    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /** Temporal 설정으로 java.util.Date가 java.sql.Date로 매핑 가능하다. */
    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTH_DATE")
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Version //version 애트리뷰트를 제어 수단으로 사용하는 낙관적 잠금 매커티즘을 사용하게 한다.
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }
    /** 하이버네이트가 레코드를 수정할 떄마다 엔터티 인스턴스의 version과 데이터베이스 레코드의 version을 비교하여,
     * 반약 버전이 같으면 이전에 아무도 수정하지 않았다는 뜻이므로 하이버네이트가 데이터를 수정하며 version을 증가시킨다.
     * 반면 버전이 같지 않으면 이전에 누군가가 데이터베이스 레코드를 수정했다는 뜻이므로
     * 하이버네이트가 StableObjectStateException 예외를 던지며,
     * 스프링은 이 예외를 HibernateOptimisticLockingFailureException으로 변환한다.
     * 예제에서는 버전관리에 정수값을 사용했으나 타임스탬프도 사용할 수 있다.
     */

    public void setVersion(int version) {
        this.version = version;
    }



    /** 7.3.2 일대다 매핑.
     * @OneToMany : 일대 다 관계를 나타내는 애너테이션.
     * cascade : 수정 작업이 수정할 테이블부터 관련 있는 자식 테이블의 레코드까지 "모두 전이돼야 함(cascade)"을 나타냄.
     * orphanRemoval : Set에 들어있는 앨범이 수정됐을 때 더이상 존재하지 않는 앨범 레코드를 데이터베이스에서 삭제해야 함을 표시.
     */
//    @OneToMany(mappedBy = "singer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)

    /**
     *  fetch = FetchType.EAGER 를 제외한 이유는 singer를 불러올때마다 다 불러오면 비효율적이기 때문. 그래서 NamedQuery로 대체함.
     *  7.6 데이터 수정에서 orphanRemoval = true 애트리뷰트를 지정했기 때문에 하이버네이트는
     *  데이터베이스에 객체를 저장할 때 데이터베이스에는 존재하지만 객체에는 더이상 존재하지 않는 모든 고아 레코드를 삭제한다.
     */
    @OneToMany(mappedBy = "singer", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public boolean addAlbum(Album album) {
        album.setSinger(this);
        return getAlbums().add(album);
    }

    public void removeAlbum(Album album) {
        getAlbums().remove(album);
    }

    /** 7.3.3 다대다 매핑.
     *  JoinTable : 하이버네이트가 검색해야 할 조인 테이블을 지정.
     */
//    @ManyToMany(fetch = FetchType.EAGER)
    @ManyToMany
    @JoinTable(name="singer_instrument",
               joinColumns = @JoinColumn(name="SINGER_ID"),
               inverseJoinColumns = @JoinColumn(name="INSTRUMENT_ID"))
    public Set<Instrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(Set<Instrument> instruments) {
        this.instruments = instruments;
    }

    public boolean addInstrument(Instrument instrument) {
        instrument.setSinger(this);
        return getInstruments().add(instrument);
    }

    public void removeInstrument(Instrument instrument) {
        getInstruments().remove(instrument);
    }

    @Override
    public String toString() {
        return "==========Singer{" +
                "id=" + singerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", version=" + version +
                '}';
    }
}
