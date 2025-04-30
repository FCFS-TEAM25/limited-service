/*
  기능 목적 : 한정판매 상품을 구매 시, 재고가 감소되고, 주문이 생성된다.
  전제 조건 : 사용자가 로그인 되어있다. / 재고가 100개인 상품이 있다. / 중복 구매는 불가능 하다. / ACTIVE 상태인 이벤트만 구매 가능하다.
  행동 : 사용자가 로그인 된 상태에서 quantity = 1 로 해당하는 limitedEventId 에 대해 구매를 요청한다.
  예상 결과 : 재고 1개 감소 시 주문이 1건 생성된다.
  예외 시나리오 :재고가 0일때 주문시도시 예외처리 / 로그인하지 않은 상태라면 예외처리
*/
import http from 'k6/http';
import {check} from 'k6';
import {Counter, Trend} from 'k6/metrics';

export const options = {
  vus: 1000,
  duration: '30s'
};

export const totalRequests = new Counter('total_requests');
export const successOrder = new Counter('success_orders');
export const quantityOutOrder = new Counter('quantity_out_orders');
export const failedOrder = new Counter('failed_orders');
export const duplicationOrder = new Counter('duplication_orders');

export const successResponseTime = new Trend('success_response_time');
export const quantityOutTime = new Trend('quantity_out_time');
export const failedResponseTime = new Trend('failed_response_time');

export default function () {

  const purchaseUrl = `http://limited-service:19095/api/v1/limited-events/2c76e9e4-b19f-4fb9-b275-3bdf910492bb/purchase`;

  const payload = JSON.stringify({
    quantity: 1
  });

  // 한정판매 구매 요청
  const purchaseRes = http.post(purchaseUrl, payload, {
    headers: {
      'Content-Type': 'application/json',
      'X-User-Id': Math.floor(Math.random() * 5000) + 11000
    }
  });

  totalRequests.add(1);

  check(purchaseRes, {

    '주문 생성 성공 (200)': (r) => {
      const ok = r.status === 200;
      if (ok) {
        successOrder.add(1);
        successResponseTime.add(purchaseRes.timings.duration);
      }
      return ok;
    },

    '재고 없음 (400)': (r) => {
      const noQuantity = r.status === 400;
      if (noQuantity) {
        quantityOutOrder.add(1);
        quantityOutTime.add(purchaseRes.timings.duration);
      }
      return noQuantity;
    },
    '중복 구매 방지 (409)': (r) => {
      const duplicationUserId = r.status === 409;
      if (duplicationUserId) {
        duplicationOrder.add(1);
      }
      return duplicationUserId;
    },

    '요청 실패': (r) => {
      const error = r.status !== 200 && r.status !== 400 && r.status !== 409;
      if (error) {
        failedOrder.add(1);
        failedResponseTime.add(purchaseRes.timings.duration);
      }
      return error;
    },
  });

}


