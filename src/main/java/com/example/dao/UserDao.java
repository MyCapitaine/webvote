package com.example.dao;

/**
 * Created by hasee on 2017/3/6.
 */

        import com.example.entity.User;
        import org.springframework.data.jpa.repository.JpaRepository;
        import javax.transaction.Transactional;
        import java.math.BigDecimal;
        import java.util.Date;
        import java.util.List;

@Transactional
public interface UserDao extends JpaRepository<User, Integer> {
    //public User findOne(Integer id);
    public List<User> findByName(String name);
    public List<User> findBySex(char sex);
    public List<User> findByBirthday(Date birthday);
    public List<User> findBySendtime(Date sendtime);
    public List<User> findByPrice(BigDecimal price);
    public List<User> findByFloatprice(float floatprice);
    public List<User> findByDoubleprice(double doubleprice);
}
