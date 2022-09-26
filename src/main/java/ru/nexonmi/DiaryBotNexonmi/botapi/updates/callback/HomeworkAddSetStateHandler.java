package ru.nexonmi.DiaryBotNexonmi.botapi.updates.callback;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MessageService;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.message.state.HomeworkAddHandler;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.GetUserInterface;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.SaveUserInterface;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.StateEnum;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.UserEntity;

public class HomeworkAddSetStateHandler extends InputCallbackHandler implements GetUserInterface, SaveUserInterface {

    private final static String LESSON_STARTS = "LesID:";

    public HomeworkAddSetStateHandler(MessageService messageService, DataRepository repository) {
        super(messageService, repository);
    }

    @Override
    protected BotApiMethod<?> handleCallback(CallbackQuery callback) {
        try {
            UserEntity user = get(callback.getMessage().getChatId());
            user.setState(StateEnum.ADD_HOMEWORK);
            user.getState().setDetails(
                    HomeworkAddHandler.packStateDescription(unpackLessonData(callback.getData()))
            );
            save(user);

            return messageService.getEditMessage(
                    callback.getMessage().getChatId(),
                    callback.getMessage().getMessageId(),
                    messageService.getSourceText(CallbackEnum.HOMEWORK_ADD_SET_STATE.replayCode) +
                    user.getDiary().getUserLessons().get(unpackLessonData(callback.getData())).getName(),
                    null);
        } catch (Exception e) {
            return messageService.getReplyMessage(callback.getMessage().getChatId(),
                    messageService.getSourceText("replay.some_error"));
        }
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

    public static String packLessonData(int lessonId) {
        return String.format("%s%d", LESSON_STARTS, lessonId);
        //replace char " " to "_" cause that is restricted to place " " in button callback
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
