package robotic.system.forum.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import robotic.system.forum.domain.model.EditHistory;
import robotic.system.forum.repository.EditHistoryRepository;

import java.util.List;

@Service
public class EditHistoryService {

    @Autowired
    private EditHistoryRepository editHistoryRepository;

    public EditHistory createEditHistory(EditHistory editHistory) {
        return editHistoryRepository.save(editHistory);
    }

    public List<EditHistory> getAllEditHistories() {
        return editHistoryRepository.findAll();
    }
}
