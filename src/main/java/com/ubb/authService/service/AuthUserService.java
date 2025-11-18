package com.ubb.authService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubb.userModule.user.dto.AddRoleToUserDto;
import com.ubb.authService.dto.UserRolesDto;
import com.ubb.userModule.user.dto.UserDataDto;
import com.ubb.authService.kafka.producer.KafkaProducer;
import com.ubb.userModule.user.service.UserCrudService;
import com.ubb.userModule.user.entity.ApplicationUser;
import com.ubb.userModule.user.entity.Role;
import com.ubb.userModule.user.kafka.ReplicationMessage;
import com.ubb.userModule.user.kafka.ReplicationMessageBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AuthUserService {
    @Value("${kafka.topic-name}")
    protected String kafkaTopicName;

    @Autowired
    protected KafkaProducer producer;

    @Autowired
    protected UserCrudService userCrudService;

    @Autowired
    protected ObjectMapper mapper;

    public UserDataDto updateUser(UserDataDto input) throws JsonProcessingException {
        ApplicationUser result = userCrudService.updateUser(input);
        updateUserData(input);
        return UserDataDto.builder()
                .email(result.getEmail())
                .fullName(result.getFullName())
                .build();
    }

    @Transactional
    public UserRolesDto addRoleToUser(AddRoleToUserDto input) throws JsonProcessingException {
        ApplicationUser result = userCrudService.addRoleToUser(input.getEmail(), input.getAuthority());
        updateUserRoleData(input);
        return UserRolesDto.builder()
                .roles(result.getRoles().stream().map(Role::getAuthority).collect(Collectors.toSet()))
                .build();
    }

    protected void updateUserData(UserDataDto user) throws JsonProcessingException {
        ReplicationMessage<?> replicationMessage = ReplicationMessageBuilder.buildMessage(user);
        producer.sendMessage(kafkaTopicName, mapper.writeValueAsString(replicationMessage));
    }

    protected void updateUserRoleData(AddRoleToUserDto input) throws JsonProcessingException {
        ReplicationMessage<?> replicationMessage = ReplicationMessageBuilder.buildMessage(input);
        producer.sendMessage(kafkaTopicName, mapper.writeValueAsString(replicationMessage));
    }
}
