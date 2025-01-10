package dev.antonis.spring_lab2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "location", schema = "mydatabase")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "is_private", nullable = false)
    private Boolean isPrivate = false;

    @Size(max = 255)
    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "coordinate", columnDefinition = "geometry not null")
    private Point<G2D> coordinate;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @NotNull
    @Column(name = "created_at")
    private Instant createdAt;
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "latest_update")
    private Instant latestUpdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category_id) {
        this.category = category_id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getLatestUpdate() {
        return latestUpdate;
    }

    public void setLatestUpdate(Instant latestUpdate) {
        this.latestUpdate = latestUpdate;
    }

    public Point<G2D> getCoordinate() {return coordinate;}

    public void setCoordinate(Point<G2D> coordinate) {this.coordinate = coordinate;}
}