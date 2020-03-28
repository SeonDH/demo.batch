package com.example.batch.entity;

import com.example.batch.configuration.model.RequestBody;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "Externals")
@ToString
public class External {
    @Id
    @Column(name = "id")
    Long id;
    @Column(name = "external_data")
    String externalData;

    public RequestBody toRequest() {
        RequestBody request = new RequestBody();
        request.setId(this.id);
        request.setData(this.externalData);
        return request;
    }

    public Custom toCustom() {
        Custom custom = new Custom();
        custom.setId(this.id);
        custom.setCustomData(this.externalData);
        return custom;
    }
}
