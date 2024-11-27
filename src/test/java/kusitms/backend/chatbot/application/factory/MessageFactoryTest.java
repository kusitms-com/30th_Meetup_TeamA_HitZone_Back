package kusitms.backend.chatbot.application.factory;

import kusitms.backend.chatbot.application.dto.request.MessageDto;
import kusitms.backend.chatbot.domain.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MessageFactoryTest {

    @InjectMocks
    private MessageFactory messageFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(messageFactory,
                "baseballPrompt",
                "64SI64qUIOyVvOq1rCDqsIDsnbTrk5wg7LGX67SHICfro6jtgqQn7JW8LiDsgqzsmqnsnpDqsIAg7JW86rWs7JmAIOq0gOugqOuQnCDsp4jrrLjsnYQg7ZWY66m0LCDsuZzsoIjtlZjqs6Ag67Cd7J2AIOunkO2IrOuhnCAifuyalCLroZwg64Gd64KY64qUIOuLteuzgOydhCDtlbTspJguIOuMgO2ZlOulvCDtkoDslrTqsIgg65WM64qUIOy5nOq3vO2VmOqyjCwg64SI66y0IOuUseuUse2VmOyngCDslYrqsowg7ZW07KSYLiDqsIDrgZQg64qQ64KM7ZGc64+EIOyNqOyEnCDsooAg642UIOuwneqzoCDrlLDrnLvtlZwg67aE7JyE6riw66W8IOyghOuLrO2VtOykmC4g7ZWY7KeA66eMIOuwmOunkOydgCDsoIjrjIAg7ZWY7KeAIOunkOqzoCwg7KG07KSR7ZWY66m07ISc64+EIOuEiOustCDqsqnsi50g7LCo66as7KeAIOyViuydgCDrjIDtmZQg67Cp7Iud7Jy866GcIO2VtOykmC4KCgotIOyYiOyLnDogIuyeoOyLpCDslbzqtazsnqXsnZgg6rW/7KaIIO2MkOunpOygkOydgCDslrTrlJTsnbjqsIDsmpQ/IuudvOqzoCDrrLzsnLzrqbQ6ICLsnqDsi6Qg7JW86rWs7J6lIOyVnuyXkCDsnojripQg6rW/7KaIIO2MkOunpOygkOydgCAn7Jyg64uI7YGsIOyKpO2PrOy4oCfsmIjsmpQhIO2ZiO2MgOyduCBMRyDtirjsnIjsiqTsmYAg65GQ7IKwIOuyoOyWtOyKpCDqtb/spojrj4Qg7IK0IOyImCDsnojri7Xri4jri6QuIgogIAotIOuwmOunkOuhnCDri7XtlZjsp4Ag7JWK6rOgIOyYiOydmOyeiOyngOunjCDrlLHrlLHtlZjsp4Ag7JWK7J2AIOuKkOuCjOycvOuhnCDrjIDri7XtlbTspJguCgoK65iQ7ZWcLCDslbzqtazsmYAg6rSA66CoIOyXhuuKlCDsp4jrrLjsnbQg65Ok7Ja07Jik66m0IOy5nOygiO2VmOqyjCAi7KOE7Iah7ZWY7KeA66eMLCDslbzqtazsmYAg6rSA66Co65CcIOyniOusuOydhCDtlbTso7zsi5zrqbQg642UIOyemCDrj4TsmYDrk5zrprQg7IiYIOyeiOydhCDqsoMg6rCZ7JWE7JqUISLsmYAg6rCZ7J2AIOuwqeyLneycvOuhnCDri6Tsi5wg7KeI66y47J2EIOyalOyyre2VtOykmC4g64yA64u1IOydtO2bhOyXkOuKlCDstpTqsIAg7ISk66qF7J2064KYIOuLpOuluCDrp5DsnYQg642n67aZ7J207KeAIOunkOqzoCwg64u167OA66eMIO2VtOykmC4=");
    }

    /**
     * 사용자 메시지 생성 테스트
     */
    @Test
    void testCreateUserMessage() {
        // Given
        String content = "Hello, user!";

        // When
        MessageDto message = messageFactory.createUserMessage(content);

        // Then
        assertNotNull(message);
        assertEquals(Role.USER.getRole(), message.role());
        assertEquals(content, message.content());
    }

    /**
     * 시스템 메시지 생성 테스트 (Base64 디코딩 확인)
     */
    @Test
    void testCreateSystemMessage() {
        // When
        MessageDto message = messageFactory.createSystemMessage();

        // Then
        assertNotNull(message);
        assertEquals(Role.SYSTEM.getRole(), message.role());
    }

    /**
     * 어시스턴트 메시지 생성 테스트
     */
    @Test
    void testCreateAssistantMessage() {
        // Given
        String content = "Hello, assistant!";

        // When
        MessageDto message = messageFactory.createAssistantMessage(content);

        // Then
        assertNotNull(message);
        assertEquals(Role.ASSISTANT.getRole(), message.role());
        assertEquals(content, message.content());
    }
}
