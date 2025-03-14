package kusitms.backend.chatbot.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ParkingGuideAnswer implements GuideAnswer {

    Q1_LG(1, "lg",
            """
            가장 가까운 지하철 출구 : 2호선 ‘종합운동장역’의 5번 출구
            • 5번 출구로 나오면 바로 중앙매표소 발견 가능해요. (매표소는 평일에는 경기 시작 1시간 30분 전, 주말에는 경기 시작 2시간 전에 열어요!)
            • 무인 매표소는 1루 쪽(중앙매표소 기준 왼쪽)으로 가면 위치해 있어요!
            • 5번 출구로 나오자마자 왼쪽으로 가면 1루, 오른쪽으로 가면 3루로 들어갈 수 있어요!""",
            "https://kr.object.ncloudstorage.com/hitzone-bucket/hitzone/chatbot/go_to_lg.webp",
            null,
            null),

    Q1_KT(1, "kt",
            """
            수원역에서 버스를 통해 KT위즈파크에 도착할 수 있어요!

            1. 수원역 1,4번 출구 방향 지하도를 건넘
            • 다이소, 아트박스 앞 버스 정류장
            • 버스 777번, 310번 : ‘종합운동장’ 정류장 하차
            • 버스 5번, 301번 : ‘로얄 팰리스’ 정류장 하차

            2. 수원역 2,4번 출구 방향 지하도를 건넘
            • 던킨도너츠, 고봉민 김밥 앞 정류장
            • 버스 2007번, 7770번 : ‘종합운동장’ 정류장 하차""",
            null,
            null,
            null),

    Q1_NONE(1, null,
            """
            해당 구장에 대한 정보는 준비 중입니다. 빠른 시일 내에 정확한 정보를 준비해 안내드릴게요!""",
            null,
            null,
            null),

    Q2_LG(2, "lg",
            """
            🚨2026.12(예상)까지 주경기장 리모델링 공사로 버스,대형화물차 이용 제한 및 제1, 제4, 제5주차장 이용 불가

            입구 바로 오른쪽에 위치한 제2주차장과 잠실새내역 방면 종합운동장사거리 제3주차장을 이용하세요!
            잠실종합운동장 부설주차장은 경기 3시간 전부터 주차가 가능해요 (야구 관람시 ‘프로야구’ 경우에 해당)""",
            "https://kr.object.ncloudstorage.com/hitzone-bucket/hitzone/chatbot/lg_parking_pee.webp",
            null,
            null),

    Q2_KT(2, "kt",
            """
            KT위즈파크는 프로야구 경기일에만 주차예약제 시행하고 있어요!

            1. 예약방법
            • KT wiz, 수원종합운동장 홈페이지를 통한 예약
            • 수정 및 취소 가능

            2. 예약 및 문의
            • 수원종합운동장 031-240-2720-1

            3. 예약 기간
            • 수원 홈경기 프로야구 입장권 예매기간 (KT wiz 홈경기 7일전 14시 00분부터 만차시)

            4. 예약 제외 차량
            • 25인승 소형버스 이상 및 장애인 차량 (입차 시 요금만 납부)

            5. 사전 예약 요금
            • 5,000원""",
            null,
            null,
            null),

    Q2_NONE(2, null,
            """
            해당 구장에 대한 정보는 준비 중입니다. 빠른 시일 내에 정확한 정보를 준비해 안내드릴게요!""",
            null,
            null,
            null),

    Q3_LG(3, "lg",
            """
            1. 탄천공영주차장
            • 운영시간
              • 24시간 운영
            • 요금
              • 기본요금 : 5분에 100원
              • 추가요금 : 5분당 100원

            2. 아시아공원 공영주차장
            • 운영시간
              • 24시간 운영
            • 요금
              • 기본요금 : 5분당 250원
              • 월정기 : 240,000원

            3. 봉상시길 노상 공영주차장
            • 운영시간
              • 9:00 ~ 21:00
            • 요금
              • 기본요금 : 5분당 250원
              • 출차 전 사전 정산""",
            null,
            null,
            null),

    Q3_KT(3, "kt",
            """
            1. 만석공원 공영주차장
            • 운영시간
              • 24시간
            • 요금
              • 최초 3시간 : 1,000원
              • 이후 3시간 : 1,000원
              • 1시간 요금 : 1,000원
              • 1일 최대 요금 : 5,000원 (야구 티켓 확인 시 1일 주차 3,000원 가능)

            2. 장안구청 부설주차장
            • 운영시간
              • 평일
                • 6:00 ~ 24:00
              • 토요일
                • 8:00 ~ 24:00
              • 일요일
                • 11:00~19:00
              • 행사/야구경기 등 주차가 필요한 경우 운영시간 조정
            • 요금
              • 기본요금 : 30분 600원
              • 추가요금 : 10분당 300원
              • 1시간 요금 : 1,500원
              • 1일 최대요금 : 7,000원

            3. 홈플러스 북수원점
            • 운영시간
              • 10:00 ~ 24:00
            • 요금
              • 무료회차 시간 : 30분 이내
              • 기본요금 : 10분당 1,000원
              • 1회 최대요금 : 30,000원""",
            null,
            null,
            null),

    Q3_NONE(3, null,
            """
            해당 구장에 대한 정보는 준비 중입니다. 빠른 시일 내에 정확한 정보를 준비해 안내드릴게요!""",
            null,
            null,
            null),

    Q4_KT(4, "kt",
            """
            KT위즈파크는 프로야구 경기일에만 주차예약제 시행하고 있어요!
            • 예약방법
              • KT wiz, 수원종합운동장 홈페이지를 통한 예약
              • 수정 및 취소 가능
            • 예약 및 문의
              • 수원종합운동장 (031-240-2720-1)
            • 예약 기간
              • 수원 홈경기 프로야구 입장권 예매기간 (KT wiz 홈경기 7일전 14시 00분부터 만차시)
            • 예약 제외 차량
              • 25인승 소형버스 이상 및 장애인 차량 (입차 시 요금만 납부)
            • 사전 예약 요금
              • 5,000원""",
            null,
            "주차 예약하러 가기",
            "https://suwonparkingbaseball.or.kr/suwonwps/EgovPageLink.do?link=main/menu/prs/ParkingReservationPrivacy"),

    Q4_NONE(4, null,
            """
            해당 구장에 대한 정보는 준비 중입니다. 빠른 시일 내에 정확한 정보를 준비해 안내드릴게요!""",
            null,
            null,
            null);

    private final int id;
    private final String stadiumName;
    private final String answer;
    private final String imgUrl;
    private final String linkName;
    private final String link;
}
