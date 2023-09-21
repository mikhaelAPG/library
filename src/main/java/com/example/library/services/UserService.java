package com.example.library.services;

import com.example.library.dto.request.UserRequest;
import com.example.library.model.User;
import com.example.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Fungsi untuk menampilkan seluruh data user(member/employee)
    public List<User> userList() {
        return userRepository.findAll();
    }

    // Fungsi untuk menampikan data user(member/employee) berdasarkan id
    public User getUserById(Long id) {
        Optional<User> users = userRepository.findById(id);

        if (users.isPresent()) {
            return users.get();
        } else {
            return null;
        }
    }

    // Fungsi untuk menambah data user(member/employee)
    public User addUser(UserRequest request) {
        // Validasi input
        if (isEmptyOrSpace(request.getName()) || isEmptyOrSpace(request.getAddress())
                || !isValidGender(request.getGender()) || !isValidPhoneNumber(request.getPhone())
                || !isValidUserType(request.getType()) || containsSpecialCharacter(request.getName())) {
            // Jika salah satu validasi tidak terpenuhi, kembalikan null
            return null;
        }

        User user = new User();
        user.setName(request.getName());
        user.setAddress(request.getAddress());
        user.setGender(request.getGender());
        user.setPhone(request.getPhone());
        user.setType(request.getType());

        return userRepository.save(user);
    }

    // Fungsi untuk mengupdate data user(member/employee)
    public boolean updateUser(Long id, UserRequest request) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            return false;
        } else {
            // Validasi input
            if (isEmptyOrSpace(request.getName()) || isEmptyOrSpace(request.getAddress())
                    || !isValidGender(request.getGender()) || !isValidPhoneNumber(request.getPhone())
                    || !isValidUserType(request.getType()) || containsSpecialCharacter(request.getName())) {
                // Jika salah satu validasi tidak terpenuhi, kembalikan false
                return false;
            }

            user.get().setName(request.getName());
            user.get().setAddress(request.getAddress());
            user.get().setGender(request.getGender());
            user.get().setPhone(request.getPhone());
            user.get().setType(request.getType());
            userRepository.save(user.get());
            return true;
        }
    }


    // Fungsi untuk menghapus data user(member/employee)
    public boolean deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            return false;
        } else {
            user.get().setDeletedAt(new Date());
            userRepository.save(user.get());
            return true;
        }
    }

    // Fungsi untuk mendapatkan daftar pengguna berdasarkan tipe (type)
    public List<User> getUsersByType(String type) {
        return userRepository.findByType(type);
    }

    // Fungsi untuk validasi apakah string null, kosong, atau hanya spasi
    private boolean isEmptyOrSpace(String str) {
        return str == null || str.trim().isEmpty();
    }

    // Fungsi untuk validasi apakah nomor telepon adalah angka dan memiliki panjang antara 10 hingga 13 karakter
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^\\d{10,13}$");
    }

    // Fungsi untuk validasi apakah sebuah string mengandung karakter special
    private boolean containsSpecialCharacter(String str) {
        return !str.matches("^[a-zA-Z0-9\\s]*$");
    }

    // Fungsi untuk validasi apakah Type adalah "staff" atau "member"
    private boolean isValidUserType(String type) {
        return "staff".equalsIgnoreCase(type) || "member".equalsIgnoreCase(type);
    }

    // Fungsi untuk validasi apakah Gender adalah "male" atau "female" atau "other"
    private boolean isValidGender(String gender) {
        String lowerCaseGender = gender.toLowerCase();
        return "male".equals(lowerCaseGender) || "female".equals(lowerCaseGender) || "other".equals(lowerCaseGender);
    }
}
