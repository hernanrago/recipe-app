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
public class NoteToNoteCommand implements Converter<Note, NoteCommand>{

    @Nullable
    @Override
    @Synchronized
    public NoteCommand convert(Note source) {
        if (source == null) return null;

        final NoteCommand notesCommand = new NoteCommand();
        
        notesCommand.setId(source.getId());
        notesCommand.setRecipeNotes(source.getRecipeNotes());
        
        return notesCommand;
    }
}
