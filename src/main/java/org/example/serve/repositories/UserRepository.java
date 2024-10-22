// UserRepository.java

package org.example.serve.repositories;

import org.example.serve.model.User;
import org.example.serve.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByRole(Role role);
    List<User> findByRoleAndApproved(Role role, boolean approved);
    List<User> findByApproved(boolean approved);
    Optional<User> findByEmail(String email);
}
