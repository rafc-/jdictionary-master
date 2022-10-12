package org.ryan.dictionary.graphics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.java.Log;
import org.ryan.dictionary.api.WordData;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Log
public class WordAsset {

    WordData data;

    public static List<String> wrap(String text) {
        final int CHARS_PER_LINE = 130;
        List<String> lines = new ArrayList<>(List.of(""));
        for (String word : text.split(" ")) {
            int last = lines.size() - 1;
            int lineSize = lines.get(last).length();
            if (lineSize + word.length() + 1 < CHARS_PER_LINE) {
                lines.set(last, lines.get(last) + word + " ");
            } else {
                lines.add(word + " ");
            }
        }

        return lines;
    }

    public void paint(Graphics g) {
        final int LINE_SPACING = 10;
        int x = 80 - Application.xOffset;
        int y = 100 - Application.yOffset;
        int lines = 0;

        Font font = new Font("Times New Roman", Font.PLAIN, 18);
        g.setFont(font.deriveFont(Font.BOLD, 28));
        g.setColor(Color.BLACK);
        FontMetrics metrics = g.getFontMetrics(font);
        int height = metrics.getHeight();

        String word = data.getWord();
        WordData.Phonetic[] phonetics = data.getPhonetics();
        WordData.Meaning[] meanings = data.getMeanings();

        g.drawString(word, x, y); //Word
        g.setFont(font);
        lines++;

        for (WordData.Phonetic phonetic : phonetics) {
            if (phonetic.getText() == null) break;
            g.drawString(String.format("%s", phonetic.getText()), x, y + (height + LINE_SPACING) * lines++); //Phonetic
        }

        for (WordData.Meaning meaning : meanings) {
            lines++;
            g.setFont(font.deriveFont(Font.ITALIC | Font.BOLD, 17));
            g.drawString(String.format("%s", meaning.getPartOfSpeech()), x, y + (height + LINE_SPACING) * lines++); //Part of Speech
            g.setFont(font);

            WordData.Definition[] definitions = meaning.getDefinitions();
            for (int j = 0; j < definitions.length; j++) {
                if (j >= 1) lines++;
                g.drawString(String.format("%d. ", j + 1), x + 25, y + (height + LINE_SPACING) * lines); //No. of definition

                List<String> wraps = wrap(definitions[j].getDefinition());
                for (String line : wraps) {
                    g.drawString(line, x + 65, y + (height + LINE_SPACING) * lines++); //Definition
                }

                if (definitions[j].getExample() != null) {
                    wraps = wrap("\"%s\"".formatted(definitions[j].getExample()));
                    g.drawString(wraps.remove(0), x + 65, y + (height + LINE_SPACING) * lines++);
                    for (String line : wraps) {
                        g.drawString(line, x + 65, y + (height + LINE_SPACING) * lines++); //Example
                    }
                }

                String[] synonyms = definitions[j].getSynonyms();
                if (synonyms == null || synonyms.length == 0) continue;
                StringBuilder s = new StringBuilder();
                for (String synonym : synonyms) {
                    s.append(", • ").append(synonym);
                }
                g.drawString(s.substring(2), x + 65, y + (height + LINE_SPACING) * lines++); //Synonym
            }
        }
    }
}