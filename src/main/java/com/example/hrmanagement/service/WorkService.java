package com.example.hrmanagement.service;

import com.example.hrmanagement.entity.TourniquetCompany;
import com.example.hrmanagement.entity.User;
import com.example.hrmanagement.entity.Work;
import com.example.hrmanagement.entity.enums.StatusName;
import com.example.hrmanagement.payload.Status;
import com.example.hrmanagement.payload.WorkDto;
import com.example.hrmanagement.repository.TourniquetRepository;
import com.example.hrmanagement.repository.UserRepository;
import com.example.hrmanagement.repository.WorkRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public record WorkService (WorkRepository repository,
                           UserRepository userRepository,
                           MailSender mailSender,
                           TourniquetRepository tourniquetRepository) {

    public Status create(WorkDto dto) {
        if (!userRepository.existsById(dto.getWorkerId()))
            return Status.USER_NOT_FOUND;
        User user = userRepository.getById(dto.getWorkerId());
        Work save = repository.save(
                new Work(
                        dto.getName(),
                        dto.getDescription(),
                        Date.valueOf(LocalDate.now().plusDays(dto.getExpireDay())),
                        user,
                        StatusName.NEW
                )
        );
        String message = "sizga vazifa biriktrildi http://localhost:8080/api/work/" +
                save.getId();
        sendMessage(message, user.getEmail());
        return Status.SUCCESS;
    }
    private void sendMessage(String messageText, String email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setText(messageText);
            message.setFrom("Anonymous");
            mailSender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public Status get(UUID id) {
        if (repository.existsById(id))
            return new Status("now you see there", true, repository.getById(id));
        return Status.WORK_NOT_FOUND;
    }

    public Status done(UUID id) {
        if(!repository.existsById(id))
            return Status.WORK_NOT_FOUND;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Work work = repository.getById(id);
        if (work.getWorker().getId() != user.getId())
            return Status.NON_USED_USER;
        work.setStatusName(StatusName.CURRENT);
        repository.save(work);
        setTourniquet(user, work);
        String message = "now you see the workers job sir http://localhost:8080/api/work/done?id="
                +id + " enjoy )";
        sendMessage(message, userRepository.getById(work.getCreatedBy()).getEmail());
        return Status.SUCCESS;
    }

    public Status doneWork(UUID id) {
        if(!repository.existsById(id))
            return Status.WORK_NOT_FOUND;
        Work work = repository.getById(id);
        if (work.getStatusName().equals(StatusName.DONE))
            return Status.ALREADY_FINISHED;
        work.setStatusName(StatusName.DONE);
        work.setFinishedDate(Timestamp.valueOf(LocalDateTime.now()));
        repository.save(work);
        return Status.SUCCESS;
    }

    private void setTourniquet(User user, Work work){
        tourniquetRepository.save(
                new TourniquetCompany(
                        user.getCompany(),
                        user,
                        work
                )
        );
    }

    public Status getAll() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Work> list = repository.findAllByStatusNameAndWorkerId(StatusName.NEW, user.getId());
        return new Status("your current new works", true, list);
    }

    public List<Work> getFinished() {
        return repository.findFinishedWorks();
    }

    public Object getNonFinished() {
        return repository.findNonFinishedWorks();
    }

    public Status delete(UUID id) {
        if (!repository.existsById(id))
            return Status.NOT_FOUND;
        try {
            repository.deleteById(id);
            return Status.SUCCESS;
        }catch (Exception e){
            return Status.DONT_DELETE_WITH_RELATIONSHIPS;
        }
    }

    public Status update(UUID id, WorkDto dto) {
        Optional<Work> byId = repository.findById(id);
        if (byId.isPresent()){
            if (!userRepository.existsById(dto.getWorkerId()))
                return Status.USER_NOT_FOUND;
            User user = userRepository.getById(dto.getWorkerId());
            Work save = repository.save(
                    new Work(
                            id,
                            dto.getName(),
                            dto.getDescription(),
                            Date.valueOf(LocalDate.now().plusDays(dto.getExpireDay())),
                            user,
                            StatusName.CURRENT
                    )
            );
            String message = "vazifangiz o'zgartirildi http://localhost:8080/api/work/" +
                    save.getId();
            sendMessage(message, user.getEmail());
        }
        return Status.NOT_FOUND;
    }
}
