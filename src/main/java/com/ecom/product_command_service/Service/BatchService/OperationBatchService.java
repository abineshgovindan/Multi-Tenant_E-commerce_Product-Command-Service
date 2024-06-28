package com.ecom.product_command_service.Service.BatchService;

import com.ecom.product_command_service.Dto.toDto.OperationObject;
import com.ecom.product_command_service.Dto.toDto.OperationObjectList;
import com.ecom.product_command_service.Entity.Products.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperationBatchService {

    private final ConcurrentLinkedQueue<OperationObject> operationQueue = new ConcurrentLinkedQueue<>();
    private final AtomicInteger operationCount = new AtomicInteger(0);
    private final int BATCH_SIZE = 5;
    private final String TOPIC = "product-operations";

    private final KafkaTemplate kafkaTemplate;
   private  List<OperationObject> batchContainer = new ArrayList<>();

    public void addOperation(OperationObject operation) {
        operationQueue.add(operation);
        int count = operationCount.incrementAndGet();
        if (count >= BATCH_SIZE) {
            processBatch();
            operationCount.set(0);
        }
    }

    @Scheduled(fixedRate = 10000)
    public void processBatch() {
        for (int i = 0; i < 100 && !operationQueue.isEmpty(); i++) {
            batchContainer.add(operationQueue.poll());
        }

        if (!batchContainer.isEmpty()) {
            kafkaTemplate.send(TOPIC, OperationObjectList.builder()
                            .operationObjects(batchContainer)
                    .build());
            System.out.println("-----------Befour--------"+operationCount.get());
            operationCount.addAndGet(-batchContainer.size());
            System.out.println("-----------after--------"+operationCount.get());
        }
    }


}
