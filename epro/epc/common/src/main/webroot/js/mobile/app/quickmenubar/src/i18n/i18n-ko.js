export const TOTAL_PRICE_TEXT = '구매예정금액';
export const TITLE_PCIK = '골라담기';
export const TITLE_HOPE_SHIPPING_DATE = '희망배송일';
export const TITLE_PURCHASE_REQUESTS = '구매요청사항';
export const TITLE_OPTION = '옵션';
export const TITLE_OPTION_SELECT = ({name, originalName}) => `옵션:${originalName || name}`;
export const BTN_BUY_TEXT_OUTTER = '구매하기';
export const BTN_BUY_TEXT_INNER = '바로구매';
export const BTN_BUT_TEXT_RESERVATION = '예약구매';
export const BTN_MINUS_TEXT = '감소';
export const BTN_PLUS_TEXT = '증가';
export const BTN_WISH = '위시리스트';
export const BTN_PHONE = '전화하기';
export const BTN_ARROW = '팝하기';
export const BTN_BASKET = '장바구니';
export const BTN_PERIOD_DELIVERY = '정기 배송 장바구니';
export const BTN_SELECT_DEFAULT = '선택하세요';
export const BTN_REMOVE = '삭제';
export const SOLD_OUT_TEXT_OUTTER = '품절된 상품입니다.';
export const SOLD_OUT_TEXT_INNER = '품절된 상품입니다. 빠른 시일 내 준비하겠습니다.';
export const TOAST_MESSAGE_FOR_ADD = '위시리스트에 담았습니다.';
export const PURCHASE_REQUESTS_TEXT = '요청사항을 입력하세요';
export const TOAST_MESSAGE_FOR_REMOVE = '위시리스트 담기가 취소되었습니다.';
export const DESCRIPTION_PICK = (unit) => `상품을 ${unit}개 단위로 선택해주세요.`;
export const DESCRIPTION_PICK_SELECTED = (unit) => `(상품 <em className="point1">${unit}개</em>를 더 선택해주세요.)`;
export const DESCRIPTION_HOPE_SHIPPING_DATE_CAUTION = '상품수령이나 설치를 위한 희망 배송일을 입력해주세요.';
export const DESCRIPTION_HOPE_SHIPPING_DATE_EXPLANATION = '업체 사정에 따라 해당 날짜에 배송되지 않을 수 있습니다.';
export const RESULT_MESSAGE_PICK = () => `점포 재고상황에 따라 상품구성이 상이할 수 있습니다.<br/>많은 양해 부탁드립니다.`;
export const CURRENCY_UNIT_WON = '원';
export const ERROR_MESSAGE_PCIK_QUANTITY = (minQuantity, maxQuantity) => `주문수량은 ${minQuantity}개 이상 ${maxQuantity}이하 가능합니다.`;
export const ERROR_MESSAGE_ONLY_NUMBER = '주문수량은 숫자만 입력 가능합니다.';
export const ERROR_MESSAGE_ONLY_MEMBER = 'L.POINT 통합회원 또는 롯데마트 회원만 정기배송 신청이 가능합니다. 회원 가입 후, 신청해 주세요';
export const ERROR_MESSAGE_MANUFACTURING_PRODUCT = '설치상품, 주문제작상품은 상품 상세에서 담으실 수 있습니다.';
export const ERROR_MESSAGE_EMPTY_TOTALQUANTITY = '주문 수량을 입력해 주세요.';
export const ERROR_MESSAGE_MORE_CHOOSE_PRODUCTION = (count) => `상품 ${count}개를 더 선택해주세요.`;
export const ERROR_MESSAGE_WRONG_PRODUCT_INFO = '잘못된 상품정보 입니다.';
export const ERROR_MESSAGE_ONLY_DIRECT_BUY = '바로구매만 가능한 상품입니다.';
export const ERROR_MESSAGE_DONT_DELIVERY = '배송가능한 상품이 아닙니다.';
export const ERROR_MESSAGE_SELECTED_HOPEDATE = '상품 수령이나 설치를 위한 희망배송일을 등록해 주세요';
export const ERROR_MESSAGE_ONLY_SMARTPICKUP = () => `현재 고객님 배송지가 선픽업 배송지입니다.
픽업주문만 가능합니다.
픽업주문이외의 주문은 배송지 변경 후 이용해주세요.`;
export const TEXT_QUANTITY = '수량';
export const TEXT_FREE_DELIVERY = '무료배송';
export const TEXT_PERIOD_DELIVERY = '원하는 날짜와 시간에 정기배송 받기';
export const TEXT_SOLD_OUT = '품절';


