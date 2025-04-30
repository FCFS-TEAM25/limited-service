/*
  기능 목적 : 한정판매 상품을 구매 시, 재고가 감소되고, 주문이 생성된다.
  전제 조건 : 사용자가 로그인 되어있다. / 재고가 100개인 상품이 있다.
  행동 : 사용자가 로그인 된 상태에서 quantity = 1 로 해당하는 limitedEventId 에 대해 구매를 요청한다.
  예상 결과 : 재고 1개 감소 시 주문이 1건 생성된다.
  예외 시나리오 : ACTIVE 상태가 아닌 이벤트는 예외처리 / 중복 구매를 시도하는 경우 예외처리 / 재고가 0일때 주문시도시 예외처리
*/
import http from 'k6/http';
import {check} from 'k6';
import {Counter, Trend} from 'k6/metrics';

export const options = {
  vus: 800,
  duration: '30s'
};

export const totalRequests = new Counter('total_requests');
export const successOrder = new Counter('success_orders');
export const quantityOutOrder = new Counter('quantity_out_orders');
export const failedOrder = new Counter('failed_orders');

export const successResponseTime = new Trend('success_response_time');
export const quantityOutTime = new Trend('quantity_out_time');
export const failedResponseTime = new Trend('failed_response_time');

// 로그인 + 헤더 파싱하여 data에 저장
export function setup() {

  const loginRes = http.post('http://api-gateway:8080/api/v1/auth/login',
      JSON.stringify({
        username: "test",
        password: "1234"
      }), {headers: {'Content-Type': 'application/json'}});

  const token = JSON.parse(loginRes.body).accessToken;
  const cookie = loginRes.cookies['refreshToken'][0].value;

  return {token, cookie};
}

export default function (data) {

  const token = data.token;
  const cookie = data.cookie;

  const purchaseUrl = `http://api-gateway:8080/api/v1/limited-events/865f4c7d-fe6f-4a9a-a2b1-77c971691efe/purchase`;

  const payload = JSON.stringify({
    quantity: 1
  });

  // 한정판매 구매 요청
  const purchaseRes = http.post(purchaseUrl, payload, {
    headers: {
      'Authorization': `Bearer ${token}`,
      'Cookie': `refreshToken=${cookie}`,
      'Content-Type': 'application/json'
    }
  });

  totalRequests.add(1);

  check(purchaseRes, {

    '주문 생성 성공 (201)': (r) => {
      const ok = r.status === 201;
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
    },

    '요청 실패': (r) => {
      const error = r.status !== 200 && r.status !== 400;
      if (error) {
        failedOrder.add(1);
        failedResponseTime.add(purchaseRes.timings.duration);
      }
    },
  });
}

export function handleSummary(data) {

  console.log(
      `✨ 평균 iteration 시간 : ${data.metrics.iteration_duration?.values?.avg?.toFixed(
          0) ?? '-'}ms`);
  console.log(
      `✨ iteration 누적 수 : ${data.metrics.iterations?.values?.count ?? 0}`);
  console.log(`✨ 최대 동시 VU 수 : ${data.metrics.vus_max?.values?.max ?? 0}`);

  console.log(`💁 총 요청 수: ${data.metrics.total_requests?.values?.count ?? 0}`);
  console.log(`✅ 성공한 주문 수: ${data.metrics.success_orders?.values?.count ?? 0}`);
  console.log(
      `✅ 재고 없음 처리 수: ${data.metrics.quantity_out_orders?.values?.count ?? 0}`);
  console.log(`📌 응답 실패 수: ${data.metrics.failed_orders?.values?.count ?? 0}`);

  console.log(
      `⏱️ 성공 평균 응답 시간 : ${data.metrics.success_response_time.values.avg.toFixed(
          0)}ms`);
  console.log(
      `⏱️ 재고 없음 평균 응답 시간 : ${data.metrics.quantity_out_time.values.avg.toFixed(
          0)}ms`);
  console.log(
      `⏱️ 실패 평균 응답 시간 : ${data.metrics.failed_response_time.values.avg.toFixed(
          0)}ms`);

  return {};
}


