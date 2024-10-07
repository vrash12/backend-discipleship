package org.example.serve.repositories.faithtest;
import org.example.serve.model.FaithTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.example.serve.model.FaithTestResponse;
import org.example.serve.model.User;
@Repository
public interface FaithTestRepository extends JpaRepository<FaithTest, Long> {


}

