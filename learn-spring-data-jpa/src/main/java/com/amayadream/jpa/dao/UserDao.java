package com.amayadream.jpa.dao;

import com.amayadream.jpa.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author :  Amayadream
 * @date :  2017.03.14 20:18
 */
public interface UserDao extends CrudRepository<User, Long> {


}
