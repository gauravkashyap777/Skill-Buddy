package collegeproject.demo.service;

import collegeproject.demo.entity.Exchange;
import collegeproject.demo.entity.User;
import collegeproject.demo.repository.ExchangeRepository;
import collegeproject.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ExchangeService {

    private final ExchangeRepository exchangeRepo;
    private final UserRepository userRepo;

    public ExchangeService(ExchangeRepository exchangeRepo, UserRepository userRepo) {
        this.exchangeRepo = exchangeRepo;
        this.userRepo = userRepo;
    }

    public Exchange save(Long userId, String type, String skill, String description, String partnerName) {
        User user = userRepo.findById(userId).orElseThrow();

        Exchange e = Exchange.builder()
                .user(user)
                .type(type)
                .skill(skill)
                .description(description)
                .partnerName(partnerName)
                .build();

        return exchangeRepo.save(e);
    }

    public List<Exchange> getMine(Long userId) {
        return exchangeRepo.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public List<Map<String, Object>> getMatches(Long userId) {
        User me = userRepo.findById(userId).orElseThrow();
        Set<String> myLearning = me.getLearningSkills();

        if (myLearning.isEmpty()) return Collections.emptyList();

        List<User> others = userRepo.findAll().stream()
                .filter(u -> !u.getId().equals(userId))
                .filter(u -> u.getTeachingSkills().stream().anyMatch(myLearning::contains))
                .toList();

        List<Map<String, Object>> matches = new ArrayList<>();
        for (User u : others) {
            long overlap = u.getTeachingSkills().stream()
                    .filter(myLearning::contains)
                    .count();

            Map<String, Object> m = new HashMap<>();
            m.put("name", u.getUsername());
            m.put("teaches", u.getTeachingSkills());
            m.put("learns", u.getLearningSkills());
            m.put("score", overlap);
            matches.add(m);
        }

        matches.sort((a, b) -> Long.compare(
                ((Number) b.get("score")).longValue(),
                ((Number) a.get("score")).longValue()
        ));

        return matches;
    }
}