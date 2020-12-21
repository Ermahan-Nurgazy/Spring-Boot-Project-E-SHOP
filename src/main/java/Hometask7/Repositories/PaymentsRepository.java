package Hometask7.Repositories;

import Hometask7.Entities.Payments;
import Hometask7.Entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PaymentsRepository extends JpaRepository<Payments, Long> {
}
