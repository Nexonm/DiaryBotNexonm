package ru.nexonmi.DiaryBotNexonmi.botapi.updates.message;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MessageService;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.callback.CallbackButtonEnum;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.callback.HomeworkAddSetStateHandler;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.GetUserInterface;
import ru.nexonmi.DiaryBotNexonmi.botapi.updates.service.MyInlineKeyboardButton;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.LessonEntity;
import ru.nexonmi.DiaryBotNexonmi.domain.entity.UserEntity;

public class HomeworkAddChooseLessonHandler extends InputMessageHandler implements GetUserInterface {


    public HomeworkAddChooseLessonHandler(MessageService messageService, DataRepository repository) {
        super(messageService, repository);
    }

    @Override
    protected SendMessage handleMessage(Message message) {
        try {
            UserEntity user = get(message.getChatId());

            SendMessage sendMessage =  messageService.getReplyMessage(
                    message.getChatId(),
                    MessageEnum.HOMEWORK_ADD_CHOOSE_LESSON.replayCode,
                    messageService.getReplayKeyboardInMessage(makeKeyboard(user.getDiary().getUserLessonsArray()))
            );

            return sendMessage;

        } catch (Exception e) {
            e.printStackTrace();
            return messageService.getReplyMessage(message.getChatId(),
                    messageService.getSourceText("replay.some_error"));
        }
    }

    private MyInlineKeyboardButton[][] makeKeyboard(LessonEntity[] lessons) {
        MyInlineKeyboardButton[][] arr = new MyInlineKeyboardButton[lessons.length][1];
        for (int i = 0; i < arr.length; i++) {
            arr[i][0] = new MyInlineKeyboardButton(lessons[i].getName(),
                    messageService.getSourceText(CallbackButtonEnum.HOMEWORK_ADD_SET_STATE.callbackAction.commandCode) +
                            HomeworkAddSetStateHandler.packLessonData(lessons[i].getID()));
        }
        return arr;
    }

    @Override
    public UserEntity get(long chat_id) {
        return repository.get(chat_id);
    }
}
