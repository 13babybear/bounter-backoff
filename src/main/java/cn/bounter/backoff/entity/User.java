package cn.bounter.backoff.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {

    private Long id;

    private String name;

    private Integer age;

    private String sex;
}
