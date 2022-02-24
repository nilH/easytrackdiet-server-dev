package nilH.easyTrackDiet.repository;


import org.springframework.data.r2dbc.repository.R2dbcRepository;

import nilH.easyTrackDiet.model.Role;

public interface RoleRepository extends R2dbcRepository<Role,Integer>{
    
}
