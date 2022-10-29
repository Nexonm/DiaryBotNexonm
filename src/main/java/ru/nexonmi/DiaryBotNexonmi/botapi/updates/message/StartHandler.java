package ru.nexonmi.DiaryBotNexonmi.botapi.updates.message;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.nexonmi.DiaryBotNexonmi.botapi.service.MessageService;
import ru.nexonmi.DiaryBotNexonmi.data.repository.DataRepository;

class StartHandler extends InputMessageHandler {

    public StartHandler(MessageService messageService, DataRepository repository) {
        super(messageService, repository);
    }

    @Override
    public SendMessage handleMessage(Message message) {
        return super.messageService.getReplyMessage(message.getChatId(), messageService.getSourceText(MessageEnum.START.replayCode));
    }

}
