package com.ubb.authService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubb.userModule.user.dto.RoleDto;
import com.ubb.authService.kafka.producer.KafkaProducer;
import com.ubb.userModule.user.entity.Role;
import com.ubb.userModule.user.kafka.ReplicationMessage;
import com.ubb.userModule.user.kafka.ReplicationMessageBuilder;
import com.ubb.userModule.user.repo.RoleRepository;
import com.ubb.userModule.user.role.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthRoleService {
    @Autowired
    protected RoleRepository roleRepository;

    @Value("${kafka.topic-name}")
    protected String kafkaTopicName;

    @Autowired
    protected KafkaProducer producer;

    @Autowired
    protected ObjectMapper mapper;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role updateRole(RoleDto input) throws JsonProcessingException {
        Role role = Role.builder()
                .authority(input.getAuthority())
                .build();

        Role result = roleRepository.save(role);
        replicateRoleData(input);
        return result;
    }

    public void deleteRole(RoleType authority) {
        Optional<Role> role = roleRepository.findByAuthority(authority);
        role.ifPresent(value -> roleRepository.delete(value));
    }

    protected void replicateRoleData(RoleDto role) throws JsonProcessingException {
        ReplicationMessage<?> replicationMessage = ReplicationMessageBuilder.buildMessage(role);
        producer.sendMessage(kafkaTopicName, mapper.writeValueAsString(replicationMessage));
    }
}
