package com.egs.shopping.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.egs.shopping.domain.Comment} entity. This class is used
 * in {@link com.egs.shopping.web.rest.CommentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /comments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter rate;

    private StringFilter text;

    private LongFilter productId;

    private LongFilter customerId;

    public CommentCriteria() {
    }

    public CommentCriteria(CommentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.rate = other.rate == null ? null : other.rate.copy();
        this.text = other.text == null ? null : other.text.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
    }

    @Override
    public CommentCriteria copy() {
        return new CommentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getRate() {
        return rate;
    }

    public void setRate(IntegerFilter rate) {
        this.rate = rate;
    }

    public StringFilter getText() {
        return text;
    }

    public void setText(StringFilter text) {
        this.text = text;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommentCriteria that = (CommentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(rate, that.rate) &&
            Objects.equals(text, that.text) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        rate,
        text,
        productId,
        customerId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (rate != null ? "rate=" + rate + ", " : "") +
                (text != null ? "text=" + text + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
            "}";
    }

}
