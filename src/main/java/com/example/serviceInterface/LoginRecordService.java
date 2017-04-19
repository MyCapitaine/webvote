package com.example.serviceInterface;

import com.example.entity.LoginRecord;
import com.example.entity.ServiceResult;
import org.springframework.data.domain.Pageable;

/**
 * Created by hasee on 2017/4/19.
 */
public interface LoginRecordService {
    ServiceResult add(LoginRecord loginRecord);
    ServiceResult find(int id,Pageable pageable);
}
