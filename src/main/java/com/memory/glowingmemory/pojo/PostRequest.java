package com.memory.glowingmemory.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author zc
 * 实现 Cloneable 接口并重写 Object 类中的 clone() 方法。 （拷贝基本成员属性，对于引用类型仅返回指向改地址的引用, 若想实现深度克隆 需对内部的所有子类进行克隆）
 * 实现 Serializable 接口，通过对象的序列化和反序列化实现克隆，可以实现真正的深度克隆 (实现可看 BeanUtils)
 */
@Data
//无参构造
@NoArgsConstructor
//有参构造
@AllArgsConstructor
public class PostRequest implements Serializable {

    private static final long serialVersionUID = -7786734246626033285L;

    private String requestId;

    @NotNull
    @Positive
    private Integer tenantId;

    @NotNull
    private String campaignUuid;

    //对象被反序列化时，被 transient 修饰的变量值不会被持久化和恢复   或使用 @Transient 注解
    private transient List<Map<String, Object>> data;
}
