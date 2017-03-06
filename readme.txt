×××关于ACL中对事项范围的筛选×××
用户（角色）对事项全部没有权限：
     acl_entry中没有记录
用户（角色）对事项全部有权限：
     acl_object_identity中object_id_identity字段值为0
     实现：BasicLookupStrategy的setLookupObjectIdentitiesWhereClause."(acl_object_identity.object_id_identity = ? and acl_class.class = ?)";
     改为："(acl_object_identity.object_id_identity = 0 or (acl_object_identity.object_id_identity = ? and acl_class.class = ?))";
用户（角色）对事项有部分权限：
     acl_entry中有个字段filter字段，保存Spel中的表达式值
