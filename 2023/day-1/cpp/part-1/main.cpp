#include <iostream>
#include <fstream>
#include <string>

int main()
{
    std::ifstream file("input.txt");
    if (file.is_open())
    {
        int result = 0;
        while (file.good())
        {
            // read current line
            std::string current_line = "";
            std::getline(file, current_line);
            int first_index = -1;
            int last_index = -1;
            // read each char of the line
            for (auto i = 0; i < current_line.size(); i++)
            {
                char character = current_line[i];
                if (isdigit(character))
                {
                    if (first_index == -1)
                    {
                        first_index = i;
                    }
                    else
                    {
                        last_index = i;
                    }
                }
            }
            // if there is no last_index, reuse the first one
            if (last_index == -1)
            {
                last_index = first_index;
            }
            char first_digit = current_line[first_index];
            char last_digit = current_line[last_index];
            std::string full_digit = std::string(&first_digit) + std::string(&last_digit);
            result += atoi(full_digit.c_str());
        }
        std::cout << result << "\n";
    }

    return 0;
}
