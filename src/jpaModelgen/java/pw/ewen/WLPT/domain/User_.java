package pw.ewen.WLPT.domain;

import pw.ewen.WLPT.domain.entity.Role;
import pw.ewen.WLPT.domain.entity.User;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, Role> role;
	public static volatile SingularAttribute<User, String> name;
	public static volatile SingularAttribute<User, String> id;
	public static volatile SingularAttribute<User, String> picture;

}

