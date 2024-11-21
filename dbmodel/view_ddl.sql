-- 회원별 업적점수 랭킹
CREATE VIEW user_achievement_rank
AS select
	dense_rank() OVER (ORDER BY sum(a.score) desc) AS userrank,
	ua.user_id AS user_id,
	u.nickname AS nickname,
	sum(a.score) AS score
from
	user_achievement ua
	left join achievement a on ua.achievement_id = a.achievement_id
	left join user u on ua.user_id = u.user_id
where
    ua.progress = 100
    and u.admin = 0
		and u.end_date is null
group by ua.user_id
order by score desc;

-- user_achievement에 있는 max_count 컬럼은 achievement 테이블로 이동
-- trade_board 테이블에 trade_type 컬럼 추가
-- alert 테이블에 alert_category_id 컬럼 varchar(50)으로 수정
-- user_restricted 테이블에 comment 컬럼 추가