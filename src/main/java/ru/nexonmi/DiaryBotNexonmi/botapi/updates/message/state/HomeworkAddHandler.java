package ru.nexonmi.DiaryBotNexonmi.botapi.updates.message.state;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MessageService;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.GetUserInterface;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.SaveUserInterface;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.UserEntity;

public class HomeworkAddHandler extends InputStateHandler implements GetUserInterface, SaveUserInterface {

    private final static String LESSON_STARTS = "LesID:";

    public HomeworkAddHandler(MessageService messageService, DataRepository repository) {
        super(messageService, repository);
    }

    @Override
    protected BotApiMethod<?> handleState(Message message) {
        try {
            UserEntity user = get(message.getChatId());

            user.getDiary().getUserLessons().get(
                    unpackLessonData(user.getState().getDetails())
            ).setHomework(message.getText());
            user.setState(ru.nexonmi.DiaryBotNexonmi.domain.entity.StateEnum.DEFAULT_STATE);
            save(user);
            return messageService.getReplyMessage(message.getChatId(),
                    messageService.getSourceText(StateEnum.HOMEWORK_ADD.replayCode));
        } catch (Exception e) {
            return messageService.getReplyMessage(message.getChatId(),
                    messageService.getSourceText("replay.some_error"));
        }
    }


    public static String packStateDescription(int lessonId) {
        return LESSON_STARTS + lessonId;
    }

    private int unpackLessonData(String text) {
        if (!text.contains(LESSON_STARTS)) return -1;

        int index = text.lastIndexOf(LESSON_STARTS) + LESSON_STARTS.length();
        try {
            return Integer.parseInt(text.substring(index));
        } catch (NumberFormatException e) {
//            e.printStackTrace();
            return 1;
        }
    }

    @Override
    public UserEntity get(long chat_id) {
        return repository.get(chat_id);
    }

    @Override
    public void save(UserEntity user) {
        repository.save(user);
    }
}


