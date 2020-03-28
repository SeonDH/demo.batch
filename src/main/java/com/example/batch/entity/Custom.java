package com.example.batch.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "Customs")
public class Custom {
    @Id
    @Column(name = "id")
    Long id;
    @Column(name = "custom_data")
    String customData;
}

