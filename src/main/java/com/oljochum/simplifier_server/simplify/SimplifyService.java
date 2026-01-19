package com.oljochum.simplifier_server.simplify;

public interface SimplifyService {
    public String llamaTest();
    public String callSimplipyGenerateText(SimplifyRequestDTO req);
    public String callSimplipySentenceSimplify(SimplifyRequestDTO req);
    public String callSimplipySentenceSuggest(SimplifyRequestDTO req);
    public String callSimplipySynonyms(SynonymRequestDTO req);
}
