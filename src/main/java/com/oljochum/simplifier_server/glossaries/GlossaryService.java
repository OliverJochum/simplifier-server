package com.oljochum.simplifier_server.glossaries;

import java.util.List;

public interface GlossaryService {
    public Glossary createGlossary(String name, Long userId);
    public Glossary addTermPairToGlossary(Glossary glossary, String originalTerm, String replacementTerm);
    public Glossary getGlossaryByUserId(Long glossaryId, Long userId);
    public Glossary getGlossaryById(Long glossaryId);
    public void deleteGlossaryById(Long glossaryId);
    public void deleteTermPairById(Long termPairId);
    public Glossary updateGlossaryName(Long glossaryId, String newName);
    public List<Glossary> getGlossariesByUserId(Long userId);
    public Glossary updateTermPair(Long termPairId, String newOriginalTerm, String newReplacementTerm);
    public List<TermPair> getTermPairsByGlossaryId(Long glossaryId);
    public TermPair getTermPairById(Long termPairId);
}
