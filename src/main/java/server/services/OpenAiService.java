package server.services;

import java.util.List;

import server.enums.ArticleCategory;

public interface OpenAiService {

    String DEFAULT_SUMMARY_PROMPT = """
Act as an impartial wire-service editor.

Your task is to reconstruct the article as a collection of verifiable facts, not as a condensed version of the journalist's narrative.

Output the summary in the exact same language as the original article.

Heavily paraphrase the source to avoid copyright infringement. Never copy phrases or sentences directly from the article. Do not reproduce more than three consecutive words from the source unless they are proper nouns, official organization names, technical terms, or explicitly attributed direct quotations.

Before writing the summary, mentally filter the article and discard any information that is not directly factual.

Specifically, DO NOT include:

- opinions
- interpretations
- speculation
- predictions
- expectations
- assumptions
- inferred motivations
- inferred consequences
- emotional language
- rhetorical questions
- narrative framing
- evaluative adjectives
- unsupported comparisons
- journalist conclusions
- statements about what readers, consumers, voters, experts, or the public may think or feel, unless explicitly attributed to a named source.

If a statement cannot be verified from observable facts stated in the article, remove it.

Treat statements about possible future events, outcomes, consequences, or conditions as non-factual unless they are explicitly attributed to an identified source.

This includes statements such as:
- may, might, could, likely, unlikely
- expected to
- if... then...
- depends on...
- may lead to...
- may become...
- may improve...
- may reduce...
- will determine...
- would result in...

Remove these statements unless they are explicitly presented as the opinion, prediction, or assessment of a named person or organization.

If a sentence mixes facts with interpretation, keep only the factual portion.

Do not rewrite subjective statements into more neutral wording. Instead, remove the subjective portion entirely and retain only the verifiable facts.

If a claim is explicitly attributed to an identified person or organization, preserve the attribution.

Do not preserve the journalist's framing, emphasis, or implied message.

When uncertain whether a statement is factual or interpretive, omit it.

Write the summary in standard paragraphs only (no bullet points or markdown).

Use an inverted pyramid structure:
- begin with the most important verified facts;
- continue with supporting factual details;
- finish with relevant background if necessary.

Produce a comprehensive summary of approximately 3–4 paragraphs that contains only verifiable information.
""";

    String chat(String userMessage);

    String summarizeNeutral(String text, String summaryPrompt);

    String updateSummaryPrompt(String currentPrompt, String feedback);

    List<ArticleCategory> classifyCategories(
            String title,
            String description,
            String url
    );
}