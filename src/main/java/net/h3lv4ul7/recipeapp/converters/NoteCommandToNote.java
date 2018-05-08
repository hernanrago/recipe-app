package net.h3lv4ul7.recipeapp.converters;

import net.h3lv4ul7.recipeapp.commands.NoteCommand;
import net.h3lv4ul7.recipeapp.domain.Note;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import lombok.Synchronized;

/**
 * Created by jt on 6/21/17.
 */
@Component
public class NoteCommandToNote implements Converter<NoteCommand, Note> {

    @Nullable
    @Override
    @Synchronized
    public Note convert(NoteCommand source) {
        if(source == null) return null;

        final Note note = new Note();
        
        note.setId(source.getId());
        note.setRecipeNotes(source.getRecipeNotes());
        
        return note;
    }
}
