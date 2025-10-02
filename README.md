# 🏦 Simple Banking Application (Spring Boot)

This project is a simple **Banking Application** built with **Spring Boot**.  
It provides REST APIs to perform core banking operations like opening an account, deposit, withdrawal, transfer, and viewing account details.

---

## 🚀 Features / APIs

1. **Open Account**  
   - Create a new account with:
     - Unique 10-digit account number  
     - Initial balance  

2. **Withdraw Money**  
   - Deduct balance from account  
   - Returns error if:
     - Insufficient balance  
     - Invalid account number  

3. **Deposit Money**  
   - Add balance to an account  
   - Returns error if account does not exist  

4. **Transfer Money**  
   - Transfer funds between two accounts  
   - Adjust balances for both accounts  
   - Returns error if insufficient balance or invalid account  

5. **Show Balance**  
   - Return account details and current balance  
   - Based on provided account number  

6. **Account Statement**  
   - Show transaction history in JSON format:  
   ```json
   [
     { "type": "deposit", "amount": 5000, "date": "01/01/2025" },
     { "type": "withdraw", "amount": 1000, "date": "02/01/2025" }
   ]
