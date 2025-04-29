-- 이벤트 상태 체크
if redis.call('EXISTS', KEYS[1]) ~= 1 then
  return 1
end

-- 중복 구매 체크
if redis.call ('EXISTS', KEYS[2]) == 1 then
  return 2
end

-- 재고 체크
local beforeQuantity = tonumber(redis.call('GET', KEYS[3]))
local orderQuantity = tonumber(ARGV[3])

if beforeQuantity == nil or beforeQuantity < orderQuantity then
  return 3
end

-- 재고 감소
local newQuantity = redis.call('DECRBY', KEYS[3], orderQuantity)
if newQuantity < 0 then
  redis.call('INCRBY', KEYS[3], orderQuantity)
  return 3
end

-- 구매 기록 저장 (PENDING)
redis.call('SET', KEYS[2], ARGV[2]);
redis.call('EXPIRE', KEYS[2], 900);


-- 구매 성공 시
redis.call('LPUSH', 'purchase_success_list', cjson.encode({
  limitedEventId = ARGV[4],
  userId = ARGV[5],
  quantity = ARGV[3],
  retryCount = 0
}))

return 0