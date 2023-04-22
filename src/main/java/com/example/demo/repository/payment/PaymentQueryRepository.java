package com.example.demo.repository.payment;

import com.example.demo.domain.member.Member;
import com.example.demo.domain.payment.Payment;

import java.util.LinkedHashMap;


import com.example.demo.domain.payment.PaymentGroup;
import com.example.demo.domain.utils.SearchCond;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;


import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.demo.domain.payment.QPayment.*;

@Slf4j
public class PaymentQueryRepository {
    private final JPAQueryFactory query;
    private final PaymentRepository paymentRepository;

    public PaymentQueryRepository(EntityManager em, PaymentRepository paymentRepository) {
        this.query = new JPAQueryFactory(em);
        this.paymentRepository = paymentRepository;
    }

    /**
     * 주문정보 페이지
     */
    public Map<Long, List<Payment>> showPaymentList(Long memberNum) {
        List<Payment> payments = query.selectFrom(payment)
                .where(payment.member.memberNum.eq(memberNum))
                .orderBy(payment.paymentGroup.desc())
                .fetch();

        return payments.stream()
                .collect(Collectors.groupingBy(Payment::getPaymentGroup, LinkedHashMap::new, Collectors.toList()));
    }

    /*private List<PaymentGroup> paginatePayment(List<PaymentGroup> paymentGroups, Pageable pageable){
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), paymentGroups.size());
        return paymentGroups.subList(start,end);
    }*/

    /**
     * 페이징, 검색
     */
    /*검색조건 결과*/
    private List<Payment> searchMembers(SearchCond cond){
        return query.selectFrom(payment)
                .where(searchBySelect((cond.getSearchSelect()), cond.getSearchValue()))
                .fetch();
    }
    /*페이징 값*/
    private List<Payment> paginatePayment(List<Payment> members, Pageable pageable){
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), members.size());
        return members.subList(start,end);
    }
    /*페이징 및 검색*/
    public Page<Payment> search(SearchCond cond, Pageable pageable) {
        List<Payment> searchedMembers = searchMembers(cond);
        List<Payment> paginatePayment = paginatePayment(searchedMembers, pageable);
        return new PageImpl<>(paginatePayment, pageable, searchedMembers.size());
    }

    /*검색조건*/
    private BooleanExpression searchBySelect(String searchSelect, String searchValue){
        if(StringUtils.hasText(searchSelect)&& StringUtils.hasText(searchValue)){
            switch (searchSelect){
                case "all":
                    return payment.paymentOrderNum.contains(searchValue)
                            .or(payment.member.memberName.contains(searchValue));
                case "paymentOrderNum":
                    return payment.paymentOrderNum.contains(searchValue);
                case"memberName":
                    return payment.member.memberName.contains(searchValue);
            }
        }
        return null;
    }



    public void cancelPayment(Long paymentNum, Long memberNum) {
        query.update(payment)
                .set(payment.visible, 0)
                .where(payment.paymentNum.eq(paymentNum)
                        .and(payment.member.memberNum.eq(memberNum)))
                .execute();

        // 해당 Payment에 대응하는 Product의 재고(stock)를 증가시키는 로직 추가
    }

    public void updateProductStock(Long productNum, Integer newStock) {
        query.update(payment.product)
                .set(payment.product.productStock, newStock)
                .where(payment.product.productNum.eq(productNum))
                .execute();
    }

}
