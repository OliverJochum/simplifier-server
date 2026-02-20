package com.oljochum.simplifier_server.glossaries;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oljochum.simplifier_server.users.User;
import com.oljochum.simplifier_server.users.UserService;

@Service
public class GlossaryServiceImpl implements GlossaryService {

    @Autowired
    private UserService userService;

    @Autowired
    private GlossaryRepo glossaryRepo;

    @Override
    public Glossary createGlossary(String name, Long userId) {
        User user = userService.findById(userId);
        return glossaryRepo.save(new Glossary(name, user));
    }

    @Override
    public Glossary addTermPairToGlossary(Glossary glossary, String originalTerm, String replacementTerm) {
        TermPair termPair = new TermPair(originalTerm, replacementTerm);
        termPair.setGlossary(glossary);
        glossary.getTermPairs().add(termPair);
        return glossaryRepo.save(glossary);
    }

    @Override
    public Glossary getGlossaryByUserId(Long glossaryId, Long userId) {
        return glossaryRepo.findByIdAndUserId(glossaryId, userId).orElseThrow(() -> new IllegalArgumentException("Glossary " + glossaryId + " not found for user " + userId));
    }

    @Override
    public Glossary getGlossaryById(Long glossaryId) {
        return glossaryRepo.findById(glossaryId).orElseThrow(() -> new IllegalArgumentException("Glossary " + glossaryId + " not found"));
    }

    @Override
    public void deleteGlossaryById(Long glossaryId) {
        glossaryRepo.deleteById(glossaryId);
    }

    @Override
    public void deleteTermPairById(Long termPairId) {
        glossaryRepo.deleteTermPairById(termPairId);
    }

    @Override
    public Glossary updateGlossaryName(Long glossaryId, String newName) {
        Glossary glossary = getGlossaryById(glossaryId);
        glossary.setName(newName);
        return glossaryRepo.save(glossary);
    }

    @Override
    public List<Glossary> getGlossariesByUserId(Long userId) {
        return glossaryRepo.findByUserId(userId);
    }

    @Override
    public Glossary updateTermPair(Long termPairId, String newOriginalTerm, String newReplacementTerm) {
        TermPair termPair = getTermPairById(termPairId);
        termPair.setOriginalTerm(newOriginalTerm);
        termPair.setReplacementTerm(newReplacementTerm);
        return glossaryRepo.save(termPair.getGlossary());
    }

    @Override
    public List<TermPair> getTermPairsByGlossaryId(Long glossaryId) {
        Glossary glossary = getGlossaryById(glossaryId);
        return glossary.getTermPairs();
    }

    @Override
    public TermPair getTermPairById(Long termPairId) {
        return glossaryRepo.findById(termPairId)
            .orElseThrow(() -> new IllegalArgumentException("TermPair " + termPairId + " not found"))
            .getTermPairs().stream()
            .filter(tp -> tp.getId().equals(termPairId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("TermPair " + termPairId + " not found in its glossary"));
    }

    @Override
    public String stringifyGlossary(Long glossaryId) {
        Glossary glossary = getGlossaryById(glossaryId);
        return glossary.stringify();
    }
    
}
