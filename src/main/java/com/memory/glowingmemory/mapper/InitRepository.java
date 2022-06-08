package com.memory.glowingmemory.mapper;


import com.memory.glowingmemory.pojo.LoginUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


/**
 * @author zc
 */
@Repository
public interface InitRepository extends PagingAndSortingRepository<LoginUser, Integer> {

    boolean existsByIdAndStatus(int id, int status);
}
