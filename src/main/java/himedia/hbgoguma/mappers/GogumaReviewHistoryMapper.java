package himedia.hbgoguma.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import himedia.hbgoguma.repository.vo.GogumaReviewHistory;

@Mapper
public interface GogumaReviewHistoryMapper {
//	<select id="selectAllReviews" resultType="GogumaReviewHistory">
	List<GogumaReviewHistory> selectAllReviews();
//	<select id="selectMyReviews" parameterType="Long" resultType="GogumaReviewHistory">
	List<GogumaReviewHistory> selectMyReviews(Long uid);
//	<insert id="insertReview" parameterType="GogumaReviewHistory">
	int insertReview(GogumaReviewHistory item);
//	<select id="selectByHid" parameterType="Long" resultType="GogumaReviewHistory">
	GogumaReviewHistory selectByHid(Long hid);
//	<update id="updateReview" parameterType="GogumaReviewHistory">
	int updateReview(GogumaReviewHistory item);
//	<delete id="deleteReview" parameterType="Long">
	int deleteReview(Long id);
}
