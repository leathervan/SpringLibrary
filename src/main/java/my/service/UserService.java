package my.service;

import my.entity.User;
import my.repo.RoleRepo;
import my.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Autowired
    public UserService(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    public List<User> getAll(){
        return userRepo.findAll();
    }

    public User get(int id){
        Optional<User> user = userRepo.findById(id);
        return user.orElse(null);
    }
    public User get(String email){
        Optional<User> user = userRepo.getUserByEmail(email);
        return user.orElse(null);
    }

    @Transactional
    public User save(User user){
        user.setRoleByRoleId(roleRepo.getRoleByName("user"));
        return userRepo.save(user);
    }
    @Transactional
    public User update(int id, User user){
        user.setId(id);
        return userRepo.save(user);
    }
    @Transactional
    public void delete(int id){
        userRepo.deleteById(id);
    }
}
