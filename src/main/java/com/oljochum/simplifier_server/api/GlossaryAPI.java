package com.oljochum.simplifier_server.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oljochum.simplifier_server.glossaries.Glossary;
import com.oljochum.simplifier_server.glossaries.GlossaryReqDTO;
import com.oljochum.simplifier_server.glossaries.GlossaryResDTO;
import com.oljochum.simplifier_server.glossaries.GlossaryService;
import com.oljochum.simplifier_server.glossaries.TermPairResDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/glossaries")
public class GlossaryAPI {
    @Autowired
    private GlossaryService glossaryService;

    @GetMapping("")
    public List<GlossaryResDTO> getGlossariesByUserId(@RequestParam Long userId) {
        return glossaryService.getGlossariesByUserId(userId).stream()
            .map(g -> new GlossaryResDTO(g.getId(), g.getName(), g.getTermPairs().stream()
                .map(tp -> new TermPairResDTO(tp.getId(), tp.getOriginalTerm(), tp.getReplacementTerm()))
                .toArray(TermPairResDTO[]::new)))
            .toList();
    }

    @GetMapping("/{glossaryId}")
    public GlossaryResDTO getGlossaryById(@PathVariable Long glossaryId, @RequestParam Long userId) {
        Glossary g = glossaryService.getGlossaryByUserId(glossaryId, userId);
        return new GlossaryResDTO(g.getId(), g.getName(), g.getTermPairs().stream()
            .map(tp -> new TermPairResDTO(tp.getId(), tp.getOriginalTerm(), tp.getReplacementTerm()))
            .toArray(TermPairResDTO[]::new));
    }

    @GetMapping("/{glossaryId}/termpairs")
    public TermPairResDTO[] getTermpairs(@PathVariable Long glossaryId, @RequestParam Long userId) {
        return glossaryService.getTermPairsByGlossaryId(glossaryId).stream()
            .map(tp -> new TermPairResDTO(tp.getId(), tp.getOriginalTerm(), tp.getReplacementTerm()))
            .toArray(TermPairResDTO[]::new);
    }
    
    @PostMapping("")
    public GlossaryResDTO createGlossary(@RequestBody GlossaryReqDTO glossaryReqDTO, @RequestParam Long userId) {
        Glossary glossary = glossaryService.createGlossary(glossaryReqDTO.name(), userId);
        return new GlossaryResDTO(glossary.getId(), glossary.getName(), glossary.getTermPairs().stream()
            .map(tp -> new TermPairResDTO(tp.getId(), tp.getOriginalTerm(), tp.getReplacementTerm()))
            .toArray(TermPairResDTO[]::new));
    }

    @PostMapping("/{glossaryId}/termpairs")
    public GlossaryResDTO addTermPairToGlossary(@PathVariable Long glossaryId, @RequestParam String originalTerm, @RequestParam String replacementTerm, @RequestParam Long userId) {
        Glossary glossary = glossaryService.getGlossaryByUserId(glossaryId, userId);
        glossary = glossaryService.addTermPairToGlossary(glossary, originalTerm, replacementTerm);
        return new GlossaryResDTO(glossary.getId(), glossary.getName(), glossary.getTermPairs().stream()
            .map(tp -> new TermPairResDTO(tp.getId(), tp.getOriginalTerm(), tp.getReplacementTerm()))
            .toArray(TermPairResDTO[]::new));
    }
    
}
