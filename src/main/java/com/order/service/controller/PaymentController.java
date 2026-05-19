package com.order.service.controller;

import com.order.service.dto.*;
import com.order.service.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Tag(name = "Payment Controller",
        description = "Generic Payment APIs for E-Commerce and Multi-Usecase Payment Processing")
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping("/initiate")
    @Operation(
            summary = "Initiate Payment",
            description = """
                    Generic API to initiate payment for any use case like:
                    - E-Commerce Order Payment
                    - Subscription Payment
                    - Wallet Recharge
                    - Bill Payment
                    - Service Booking
                   
                    Supported Payment Methods:
                    - UPI (GPay, PhonePe, Paytm)
                    - Credit Card
                    - Debit Card
                    - Net Banking
                    - Wallet
                    - EMI
                   \s"""
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment Initiated Successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    public ResponseEntity<PaymentInitiateResponse> initiatePayment(@Valid @RequestBody
                                                                   PaymentInitiateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.initiatePayment(request));
    }


    @GetMapping("/{paymentId}/status")
    @Operation(
            summary = "Get Payment Status",
            description = "Fetch payment status using paymentId"
    )
    public ResponseEntity<PaymentStatusResponse> getPaymentStatus(@Parameter(description = "Unique Payment Id") @PathVariable String paymentId) {
        return ResponseEntity.ok(
                paymentService.getPaymentStatus(paymentId)
        );
    }


    @PostMapping("/verify")
    @Operation(
            summary = "Verify Payment",
            description = """
                    Verify payment transaction received from payment gateway.
                    Usually used after frontend callback or webhook response.
                    """
    )
    public ResponseEntity<PaymentVerificationResponse> verifyPayment(
            @Valid @RequestBody PaymentVerificationRequest request) {

        return ResponseEntity.ok(
                paymentService.verifyPayment(request)
        );
    }


    @PostMapping("/refund")
    @Operation(
            summary = "Refund Payment",
            description = """
                    Refund complete or partial amount for a successful transaction.
                    Supports:
                    - Full Refund
                    - Partial Refund
                    """
    )
    public ResponseEntity<RefundResponse> refundPayment(@Valid @RequestBody RefundRequest request) {
        return ResponseEntity.ok(
                paymentService.refundPayment(request)
        );
    }


    @PostMapping("/callback/{provider}")
    @Operation(
            summary = "Payment Gateway Callback/Webhook",
            description = """
                    Callback API for payment gateways like:
                    - Razorpay
                    - Stripe
                    - PhonePe
                    - Paytm
                    - Cashfree
                    """
    )
    public ResponseEntity<String> paymentCallback(
            @PathVariable String provider,
            @RequestBody String payload,
            @RequestHeader(value = "X-Signature", required = false) String signature) {

        paymentService.handlePaymentCallback(provider, payload, signature);

        return ResponseEntity.ok("Callback Processed Successfully");
    }


    @PostMapping("/{paymentId}/cancel")
    @Operation(
            summary = "Cancel Payment",
            description = "Cancel initiated or pending payment"
    )
    public ResponseEntity<CommonApiResponse> cancelPayment(
            @PathVariable String paymentId) {
        return ResponseEntity.ok(paymentService.cancelPayment(paymentId));
    }
}