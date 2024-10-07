package org.example.serve.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.example.serve.model.Sermon;
import org.example.serve.repositories.SermonRepository;

@Service
public class SermonService {

    @Autowired
    private SermonRepository sermonRepository;

    public List<Sermon> getAllSermons() {
        return sermonRepository.findAll();
    }

    public Optional<Sermon> getSermonById(Long id) {
        return sermonRepository.findById(id);
    }

    public Sermon saveSermon(Sermon sermon) {
        return sermonRepository.save(sermon);
    }

    public void deleteSermon(Long id) {
        sermonRepository.deleteById(id);
    }
}
